package me.bdan.raincheck.frontend;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ApiController {
	@Value("${weather.server.host}")
	private String weatherAccessorHost;
	@Value("${weather.server.port}")
	private int weatherAccessPort;
	
	private RestTemplate restTemplate = new RestTemplate();
    @RequestMapping("/api")
    public String apiIndex() {
        return "this is a response";
    }
    
    @RequestMapping("/api/search/locations/{query}")
    public String location(@PathVariable("query") String query) {
    	URI uri = UriComponentsBuilder.fromUriString("http://")
    			.host(weatherAccessorHost)
    			.port(weatherAccessPort)
    			.path("/api/search/locations")
    			.pathSegment(query)
    			.build()
    			.toUri();
    	
    	return restTemplate.getForObject(uri, String.class);
    }

    @RequestMapping("/api/locations/{key}/conditions")
    public String conditions(@PathVariable("key") String key) {
    	URI uri = UriComponentsBuilder.fromUriString("http://")
    			.host(weatherAccessorHost)
    			.port(weatherAccessPort)
    			.path("/api/locations")
    			.pathSegment(key,"conditions")
    			.build()
    			.toUri();
    	
    	return restTemplate.getForObject(uri, String.class);
    }
    
    @RequestMapping("/api/locations/{key}/forecast")
    public String forecast(@PathVariable("key") String key) {
    	URI uri = UriComponentsBuilder.fromUriString("http://")
    			.host(weatherAccessorHost)
    			.port(weatherAccessPort)
    			.path("/api/locations")
    			.pathSegment(key,"forecast")
    			.build()
    			.toUri();
    	
    	return restTemplate.getForObject(uri, String.class);
    }

    
    @ExceptionHandler
    void handleException(Exception e, HttpServletResponse response) throws IOException {
    	Writer body = response.getWriter();
    	response.addHeader("Content-Type","text/plain");
    	body.write(e.getMessage());
    	response.setStatus(500);
    }    
}
