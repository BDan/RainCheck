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
