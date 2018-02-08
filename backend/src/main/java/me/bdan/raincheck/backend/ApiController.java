package me.bdan.raincheck.backend;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.bdan.raincheck.backend.data.Conditions;
import me.bdan.raincheck.backend.data.ForecastResponse;
import me.bdan.raincheck.backend.data.LocationResponse;



@RestController
public class ApiController {
	
	@Value("${wu.api.key}")
	private String API_KEY;
	AccessorWUnderground accessor;

	@PostConstruct
	public void initAfterStartup() {
		accessor = new AccessorWUnderground(API_KEY);
	}
	
	@RequestMapping("/")
	public String index() {
		return "Raincheck BackEnd API";
	}

	@RequestMapping("/api/search/locations/{place}")
	public @ResponseBody LocationResponse getLocation(@PathVariable(value = "place") String place) {
		return accessor.fetchLocationsForName(place);
	}

	@RequestMapping("/api/locations/{key}/conditions")
	public @ResponseBody Conditions getConditions(@PathVariable(value = "key") String locationKey) {
		return accessor.fetchConditionsForKey(locationKey);
	}

	@RequestMapping("/api/locations/{key}/forecast")
	public @ResponseBody ForecastResponse getForecast(@PathVariable(value = "key") String locationKey) {
		return accessor.fetchForecastForKey(locationKey);
	}

}