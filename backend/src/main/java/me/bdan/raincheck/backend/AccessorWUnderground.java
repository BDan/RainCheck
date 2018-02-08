package me.bdan.raincheck.backend;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.web.client.RestTemplate;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import me.bdan.raincheck.backend.data.Conditions;
import me.bdan.raincheck.backend.data.ForecastDay;
import me.bdan.raincheck.backend.data.ForecastResponse;
import me.bdan.raincheck.backend.data.Location;
import me.bdan.raincheck.backend.data.LocationResponse;
import me.bdan.raincheck.backend.data.RemoteResult;


public class AccessorWUnderground {
	final private String NA = "n.a.";
	private XPath xPath = XPathFactory.newInstance().newXPath();
	private RestTemplate rtl = new RestTemplate();
	private String apiKey;

	public AccessorWUnderground(String apiKey) {
		this.apiKey = apiKey;
	}

	public Conditions fetchConditionsForKey(String locationKey) {
		String urlTemplate = "http://api.wunderground.com/api/%s/conditions/q/zmw:%s.xml";
		String url = String.format(urlTemplate, apiKey, locationKey);
		return (Conditions) fetchAndDecode(url, (xml) -> decodeWUGConditions(xml));
	}
	
	public ForecastResponse fetchForecastForKey(String locationKey) {
		String urlTemplate = "http://api.wunderground.com/api/%s/forecast/q/zmw:%s.xml";
		String url = String.format(urlTemplate, apiKey, locationKey);
		return (ForecastResponse) fetchAndDecode(url, (xml) -> decodeWUGForecast(xml));
	}
	
	
	public LocationResponse fetchLocationsForName(String name) {
		String urlTemplate = "http://autocomplete.wunderground.com/aq?format=xml&query=%s";
		String url = String.format(urlTemplate, name);
		return (LocationResponse) fetchAndDecode(url, (xml) -> decodeWUGAutoComplete(xml));
	}

	interface Decoder {
		RemoteResult decode(Document xml);
	}

	RemoteResult fetchAndDecode(String url, Decoder decoder) {
		RemoteResult retVal = new RemoteResult();
		String result = rtl.getForObject(url, String.class);
		try {
			Document xml = parseXMLString(result);
			retVal = decoder.decode(xml);
		} catch (XMLException e) {
			retVal.setSuccess(false);
			retVal.setError(e.getMessage());
		}
		return retVal;
	}

	Conditions decodeWUGConditions(Document doc) {
		Conditions retVal = new Conditions();
		
		retVal = (Conditions)checkError(doc, retVal);
		if (!retVal.isSuccess()) {
			return retVal;
		}
		retVal.setTempC(getString(doc, "/response/current_observation/temp_c", NA));
		retVal.setWindDirection(getString(doc, "/response/current_observation/wind_dir", NA));
		retVal.setWindSpeedKmh(getString(doc, "/response/current_observation/wind_kph", NA));
		retVal.setFeelsLikeC(getString(doc, "/response/current_observation/feelslike_c", NA));
		retVal.setPressureMb(getString(doc, "/response/current_observation/pressure_mb", NA));
		retVal.setRelativeHumidity(getString(doc, "/response/current_observation/relative_humidity", NA));
		return retVal;
	}

	LocationResponse decodeWUGAutoComplete(Document doc) {
		LocationResponse retVal = new LocationResponse();
		retVal.setSuccess(true);
		List<Location> locations = retVal.getLocations();
		try {
			NodeList results = doc.getElementsByTagName("RESULTS");
			if (results.getLength() != 1) {
				return retVal;
			}
			// Note: XML version of Weather Underground's AutoComplete call is slightly
			// broken, the response should be decoded manually
			NodeList nList = results.item(0).getChildNodes();
			Location tmpLocation = null;
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == 1) {
					String tag = nNode.getNodeName();
					String text = nNode.getTextContent();
					if (tag.equals("name")) {
						if (tmpLocation != null && tmpLocation.isValid()) {
							locations.add(tmpLocation);
						}
						tmpLocation = new Location();
						tmpLocation.setCity(text);
					} else if (tag.equals("c")) {
						tmpLocation.setCountry(text);
					} else if (tag.equals("type")) {
						tmpLocation.setValid(text.equals("city"));
					} else if (tag.equals("zmw")) {
						tmpLocation.setKey(text);
					}
				}
			}
			if (tmpLocation != null && tmpLocation.isValid()) {
				locations.add(tmpLocation);
			}

		} catch (Exception e) {
			retVal.setError(e.getMessage());
			retVal.setSuccess(false);
		}
		retVal.setLocations(locations);
		return retVal;

	}

	ForecastResponse decodeWUGForecast(Document doc)  {
		ForecastResponse retVal = new ForecastResponse();
		retVal = (ForecastResponse)checkError(doc, retVal);
		if (!retVal.isSuccess()) {
			return retVal;
		}
		try {
		NodeList nodes = (NodeList) xPath.compile("/response/forecast/simpleforecast/forecastdays/forecastday").evaluate(doc, XPathConstants.NODESET);
		List<ForecastDay> days = retVal.getForecastDays();
		for (int i=0;i<nodes.getLength();i++) {
			Node node = nodes.item(i);
			ForecastDay fd = new ForecastDay();
			fd.setMaxC(getString(node,"high/celsius",NA));
			fd.setMinC(getString(node,"low/celsius",NA));
			String date = new StringBuilder()
					.append(getString(node,"date/day",NA))
					.append("/")
					.append(getString(node,"date/monthname_short",NA))
					.append("/")
					.append(getString(node,"date/year",NA))
					.toString();
			fd.setDate(date);
			fd.setAverageWindKmh(getString(node,"avewind/kph",NA));
			days.add(fd);
			
		}
		} catch (XPathExpressionException e) {
			retVal.setError(e.getMessage());
			retVal.setSuccess(false);
		}

		return retVal;
	}


	String getString(Object xml, String path) throws XMLException {
		try {
			Node node = (Node) xPath.compile(path).evaluate(xml, XPathConstants.NODE);
			if (node != null) {
				return node.getTextContent();
			} else {
				throw new XMLException("XPath node not found: " + path);
			}
		} catch (DOMException | XPathExpressionException e) {
			throw new XMLException("Error evaluating XPath: " + path, e);
		}

	}

	String getString(Object xml, String path, String _default) {
		try {
			return getString(xml, path);
		} catch (XMLException e) {
			return _default;
		}
	}

	Document parseXMLString(String str) throws XMLException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setValidating(false);
		builderFactory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder;
		try {
			builder = builderFactory.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(str.getBytes());
			return builder.parse(is);
		} catch (IOException | SAXException | ParserConfigurationException e) {
			throw new XMLException(e.getMessage());
		}
	}

	RemoteResult checkError(Object xml, RemoteResult result) {
		try {
			Node node = (Node) xPath.compile("/response/error/description").evaluate(xml, XPathConstants.NODE);
			if (node == null) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
				result.setError("Data source error: "+node.getTextContent());
			}
		} catch (DOMException | XPathExpressionException e) {
			result.setSuccess(false);
			result.setError("XML error: "+e.getMessage());
		}
		return result;
	}
	
	
}
