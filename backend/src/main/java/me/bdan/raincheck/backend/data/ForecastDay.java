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
