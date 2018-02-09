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

public class Conditions extends RemoteResult {
	private String city;
	private String stateName;
	private String coords;
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

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCoords() {
		return coords;
	}
	public void setCoords(String coords) {
		this.coords = coords;
	}
	
	
	@Override
	public String toString() {
		return "Conditions [city=" + city + ", stateName=" + stateName + ", coords=" + coords + ", tempC=" + tempC
				+ ", windDirection=" + windDirection + ", windSpeedKmh=" + windSpeedKmh + ", feelsLikeC=" + feelsLikeC
				+ ", pressureMb=" + pressureMb + ", relativeHumidity=" + relativeHumidity + ", success=" + success
				+ ", error=" + error + "]";
	}
	
	


}
