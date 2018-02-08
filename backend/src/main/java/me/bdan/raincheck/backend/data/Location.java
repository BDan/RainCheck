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
