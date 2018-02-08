package me.bdan.raincheck.frontend;

import java.net.URI;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class ApiController {
	private String weatherAccessorHost = "localhost";
	private int weatherAccessPort = 8080;
	
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
    
    
}
