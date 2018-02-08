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
package me.bdan.raincheck.backend.data;

public class Location {

	public Location() {};
	
	public Location(String city, String country, String region, String key) {
		super();
		this.city = city;
		this.country = country;
		this.region = region;
		this.key = key;
	}

	private String city;
	private String country;
	private String region;
	private String key;
	private boolean valid;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@Override
	public String toString() {
		return "Location [city=" + city + ", country=" + country + ", region=" + region + ", key=" + key + ", valid="
				+ valid + "]";
	}

}
