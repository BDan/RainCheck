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

import java.util.ArrayList;
import java.util.List;

public class ForecastResponse extends RemoteResult {
	List<ForecastDay> forecastDays= new ArrayList<>();

	@Override
	public String toString() {
		return "ForecastResponse [forecastDays=" + forecastDays + ", success=" + success + ", error=" + error + "]";
	}

	public List<ForecastDay> getForecastDays() {
		return forecastDays;
	}

	public void setForecastDays(List<ForecastDay> forecastDays) {
		this.forecastDays = forecastDays;
	}
	
}
