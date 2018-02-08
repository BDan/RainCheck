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

public class ForecastDay {
	String maxC;
	String minC;
	String date;
	String averageWindKmh;

	public String getMaxC() {
		return maxC;
	}
	public void setMaxC(String maxC) {
		this.maxC = maxC;
	}
	public String getMinC() {
		return minC;
	}
	public void setMinC(String minC) {
		this.minC = minC;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAverageWindKmh() {
		return averageWindKmh;
	}
	public void setAverageWindKmh(String averageWindKmh) {
		this.averageWindKmh = averageWindKmh;
	}
	@Override
	public String toString() {
		return "ForcastDay [maxC=" + maxC + ", minC=" + minC + ", date=" + date + ", averageWindKmh=" + averageWindKmh
				+ "]";
	}
}
