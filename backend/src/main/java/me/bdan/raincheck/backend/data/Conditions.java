package me.bdan.raincheck.backend.data;

public class Conditions extends RemoteResult {
	private String tempC;
	private String windDirection ="";
	private String windSpeedKmh;
	private String feelsLikeC;
	private String pressureMb;
	private String relativeHumidity;
	public String getTempC() {
		return tempC;
	}
	public void setTempC(String tempC) {
		this.tempC = tempC;
	}
	public String getWindDirection() {
		return windDirection;
	}
	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}
	public String getWindSpeedKmh() {
		return windSpeedKmh;
	}
	public void setWindSpeedKmh(String windSpeedKmh) {
		this.windSpeedKmh = windSpeedKmh;
	}
	public String getFeelsLikeC() {
		return feelsLikeC;
	}
	public void setFeelsLikeC(String feelsLikeC) {
		this.feelsLikeC = feelsLikeC;
	}
	public String getPressureMb() {
		return pressureMb;
	}
	public void setPressureMb(String pressureMb) {
		this.pressureMb = pressureMb;
	}
	public String getRelativeHumidity() {
		return relativeHumidity;
	}
	public void setRelativeHumidity(String relativeHumidity) {
		this.relativeHumidity = relativeHumidity;
	}
	@Override
	public String toString() {
		return "Conditions [tempC=" + tempC + ", windDirection=" + windDirection + ", windSpeedKmh=" + windSpeedKmh
				+ ", feelsLikeC=" + feelsLikeC + ", pressureMb=" + pressureMb + ", relativeHumidity=" + relativeHumidity
				+ ", success=" + success + ", error=" + error + "]";
	}
	
	


}
