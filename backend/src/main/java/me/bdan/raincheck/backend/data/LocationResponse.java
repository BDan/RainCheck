package me.bdan.raincheck.backend.data;

import java.util.ArrayList;
import java.util.List;

public class LocationResponse extends RemoteResult{
	List<Location> locations = new ArrayList<>();

	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

}

