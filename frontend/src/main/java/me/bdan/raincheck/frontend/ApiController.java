/*
 * RainCheck
 * Copyright (C) 2018, Dan Bendas
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
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


/**
 * MVC Controller for front-end API calls 
 * @author Dan Bendas
 */

@RestController
public class ApiController {
	@Value("${weather.server.host}")
	private String weatherAccessorHost;
	@Value("${weather.server.port}")
	private int weatherAccessPort;

	private RestTemplate restTemplate = new RestTemplate();
    

	@RequestMapping("/api")
    public String apiIndex() {
        return "Front-End API here!";
    }
	
    /**
     * Front-end handler for "forecast" HTTP GET calls
     * @param query Search string, the name of a geographical location, possibly incomplete (e.g. "Paris")
     * @return
     */
	
    @RequestMapping("/api/search/locations/{query}")
    public String location(@PathVariable("query") String query) {
    	URI uri = baseURI()
    			.path("/api/search/locations")
    			.pathSegment(query)
    			.build()
    			.toUri();
    	return restTemplate.getForObject(uri, String.class);
    }


    /**
     * Front-end handler for "current conditions" HTTP GET calls
     * @param key Data source compatible location code (WUnderground zmw)
     * @return
     */
    
    @RequestMapping("/api/locations/{key}/conditions")
    public String conditions(@PathVariable("key") String key) {
    	URI uri = baseURI()
    			.path("/api/locations")
    			.pathSegment(key,"conditions")
    			.build()
    			.toUri();
    	return restTemplate.getForObject(uri, String.class);
    }
    

    /**
     * Front-end handler for "forecast" HTTP GET calls
     * @param key Data source compatible location code (WUnderground zmw)
     * @return
     */
    @RequestMapping("/api/locations/{key}/forecast")
    public String forecast(@PathVariable("key") String key) {
    	URI uri = baseURI()
    			.path("/api/locations")
    			.pathSegment(key,"forecast")
    			.build()
    			.toUri();
    	return restTemplate.getForObject(uri, String.class);
    }


    /**
     * Called by the framework when an exception is thrown in another method
     * @param e Cause exception
     * @param response Blank {@link HttpServletResponse} that will be sent upon treating the error
     * @throws IOException
     */
    @ExceptionHandler
    void handleException(Exception e, HttpServletResponse response) throws IOException {
    	Writer body = response.getWriter();
    	response.addHeader("Content-Type","text/plain");
    	body.write(e.getMessage());
    	response.setStatus(500);
    }
    
    private UriComponentsBuilder baseURI() {
    	return UriComponentsBuilder.fromUriString("http://")
    			.host(weatherAccessorHost)
    			.port(weatherAccessPort);

    }
    
}
