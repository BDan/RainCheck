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
import java.util.Arrays;
import java.util.Base64;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import me.bdan.raincheck.frontend.data.AuthenticationResponse;
import me.bdan.raincheck.frontend.data.AuthorizationData;
import me.bdan.raincheck.frontend.data.BasicApiResponse;
import me.bdan.raincheck.frontend.data.JwtAuthenticationResponse;

/**
 * REST Controller for front-end API calls
 * 
 * @author Dan Bendas
 */

@RestController
@RequestMapping("/api")
public class ApiController {
	@Value("${backend.host}")
	private String weatherAccessorHost;
	@Value("${backend.port}")
	private int weatherAccessPort;
	@Value("${backend.auth.user}")
	private String backendUser;
	@Value("${backend.auth.password}")
	private String backendPassword;

	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	AuthorizationData authorizationData;

	@RequestMapping("/")
	public String apiIndex() {
		return "Front-End API here!";
	}
	
	

	/**
	 * @param query base64 encoded user:password pair
	 * @return authorisation status
	 */
	@RequestMapping("/credentials/{query}")
	public AuthenticationResponse credentials(@PathVariable("query") String query) {
		String decoded = new String(Base64.getDecoder().decode(query));
		String[] pcs = decoded.split(":");
		if (pcs.length != 2) {
			return new AuthenticationResponse(false, "Incorrect credentials");
		}

		// Authentication
		try {
			Authentication auth = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(pcs[0], pcs[1]));
			return new AuthenticationResponse(auth.isAuthenticated(), "");
		} catch (AuthenticationException e) {
			return new AuthenticationResponse(false, e.getMessage());
		}
	}

	/**
	 * Front-end handler for "forecast" HTTP GET calls
	 * 
	 * @param query
	 *            Search string, the name of a geographical location, possibly
	 *            incomplete (e.g. "Paris")
	 * @return
	 */

	@RequestMapping("/search/locations/{query}")
	@ResponseBody
	public String location(@PathVariable("query") String query) {
		URI uri = baseURI().path("/api/search/locations").pathSegment(query).build().toUri();
		return authorisedPassThrough(uri);

	}
	
	/**
	 * Forwards a HTTP GET call to the back-end server and return the result with any processing. 
	 * Performs also authentication. Retries the call in case the server authentication as changed since 
	 * previous authentication event. 
	 * @param uri
	 * @return
	 */

	String authorisedPassThrough(URI uri) {
		return authorisedPassThrough(uri,true);
		
	}
	
	
	
	/**
	 * Forwards a HTTP GET call to the back-end server and return the result with any processing. 
	 * Performs also authentication. 
	 * @param uri
 	 * @param first When "first" is true, in the improbable case of receiving a 403 status response
 	 * while in authenticated state (e.g. the front-end server has changed JWT secret), a second attempt to
 	 * fetch the data is made  
	 * @return
	 */
	
	String authorisedPassThrough(URI uri, boolean first) {

		if (authorized()) {

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Authorization", "Bearer " + authorizationData.getToken());

			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			try {
				ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
				return response.getBody();
			} catch (HttpClientErrorException e) {
				if (e.getRawStatusCode() == 403 && first) {
					authorizationData.setAuthorized(false);
					
					return authorisedPassThrough(uri, false);
				} else {
					return new BasicApiResponse(false, "Error: " + e.getMessage()).toJson();

				}
			}

		} else {
			ObjectMapper objectMapper = new ObjectMapper();
			BasicApiResponse retVal = new BasicApiResponse(false,
					"Error authenticating with front-end: " + authorizationData.getErr());
			try {
				return objectMapper.writeValueAsString(retVal);
			} catch (JsonProcessingException e) {
				return "{}";
			}
		}

	}

	/**
	 * Front-end handler for "current conditions" HTTP GET calls
	 * 
	 * @param key
	 *            Data source compatible location code (WUnderground zmw)
	 * @return
	 */

	@RequestMapping("/locations/{key}/conditions")
	public String conditions(@PathVariable("key") String key) {
		URI uri = baseURI().path("/api/locations").pathSegment(key, "conditions").build().toUri();
		return authorisedPassThrough(uri);
	}

	/**
	 * Front-end handler for "forecast" HTTP GET calls
	 * 
	 * @param key
	 *            Data source compatible location code (WUnderground zmw)
	 * @return
	 */
	@RequestMapping("/locations/{key}/forecast")
	public String forecast(@PathVariable("key") String key) {
		URI uri = baseURI().path("/api/locations").pathSegment(key, "forecast").build().toUri();
		return restTemplate.getForObject(uri, String.class);
	}

	/**
	 * Called by the framework when an exception is thrown in another method
	 * 
	 * @param e
	 *            Cause exception
	 * @param response
	 *            Blank {@link HttpServletResponse} that will be sent upon treating
	 *            the error
	 * @throws IOException
	 */
	@ExceptionHandler
	void handleException(Exception e, HttpServletResponse response) throws IOException {
		Writer body = response.getWriter();
		response.addHeader("Content-Type", "text/plain");
		body.write(e.getMessage());
		response.setStatus(500);
	}


	/**
	 * Checks if the front-end is authenticated and if not (or authorisation is expired) attempts to re-authorize
	 * @return
	 */
	public boolean authorized() {
		return authorize(backendUser, backendPassword);
	}

	/**
	 * Checks if the front-end is authenticated and if not (or authorisation is expired) attempts to re-authorize
	 * with credentials
	 * @param user
	 * @param pass
	 * @return authorisation status
	 */
	boolean authorize(String user, String pass) {

		if (authorizationData.isAuthorized() && authorizationData.getExpires() > System.currentTimeMillis() + 1000) {
			return true;
		}
		System.out.println("reauthorising");
		String credentials = new String(Base64.getEncoder().encode((user + ":" + pass).getBytes()));
		URI uri = baseURI().path("/api/authenticate").pathSegment(credentials).build().toUri();
		try {
			JwtAuthenticationResponse response = restTemplate.getForObject(uri, JwtAuthenticationResponse.class);
			if (response.isSuccess()) {
				authorizationData.setAuthorized(true);
				authorizationData.setToken(response.getToken());
				authorizationData.setExpires(response.getExpires());
			} else {
				authorizationData.setAuthorized(false);
				authorizationData.setErr(response.getMsg());
			}
		} catch (HttpClientErrorException e) {
			authorizationData.setAuthorized(false);
			ObjectMapper objectMapper = new ObjectMapper();
			String message = "";
			try {
				message = objectMapper.readTree(e.getResponseBodyAsByteArray()).path("message").toString();
			} catch (IOException e1) {
				message = e.getResponseBodyAsString();
			}
			authorizationData.setErr(String.format("HTTP Error (%d): %s", e.getRawStatusCode(), message));
		}
		return authorizationData.isAuthorized();
	}

	private UriComponentsBuilder baseURI() {
		return UriComponentsBuilder.fromUriString("http://").host(weatherAccessorHost).port(weatherAccessPort);

	}

}
