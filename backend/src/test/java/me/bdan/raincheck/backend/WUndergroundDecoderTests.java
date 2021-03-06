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
package me.bdan.raincheck.backend;

import static me.bdan.raincheck.backend.TestUtils.parseXmlFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;

import me.bdan.raincheck.backend.data.Conditions;
import me.bdan.raincheck.backend.data.ForecastDay;
import me.bdan.raincheck.backend.data.ForecastResponse;
import me.bdan.raincheck.backend.data.Location;
import me.bdan.raincheck.backend.data.LocationResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WUndergroundDecoderTests {
	final String DIR = "../test_data/api/wunderground/";
	AccessorWUnderground accessor = new AccessorWUnderground(null);;



	@Test
	public void testConditions() throws Exception {
		String fName = "conditions.xml";
		Document xml = parseXmlFile(DIR+fName); 
		Conditions c = accessor.decodeWUGConditions(xml);

		assertTrue(c.isSuccess());
		assertEquals("39", c.getTempC());
		assertEquals("South", c.getWindDirection());
		assertEquals("11", c.getWindSpeedKmh());
		assertEquals("39", c.getFeelsLikeC());
		assertEquals("1003", c.getPressureMb());
		assertEquals("4%", c.getRelativeHumidity());
		assertEquals("Oului", c.getCity());
		assertEquals("Central African Republic", c.getStateName());
		assertEquals("7.44000006,15.84000015", c.getCoords());
	}

	@Test
	public void testConditionsBadKey() throws Exception {
		String fName = "conditions_badkey.xml";
		Document xml = parseXmlFile(DIR+fName); 
		Conditions c = accessor.decodeWUGConditions(xml);
		assertEquals(false, c.isSuccess());
	}
	
	@Test
	public void testLocationSearch() throws Exception {
		String fName = "autocomplete.xml";
		Document xml = parseXmlFile(DIR+fName); 
		LocationResponse lr = accessor.decodeWUGAutoComplete(xml);
		
		assertTrue(lr.isSuccess());
		List<Location> locations = lr.getLocations();
		assertNotEquals(locations.size(), 0);
		Location l = locations.get(0);
		assertEquals(l.getCity(),"Helsinki, Finland");
		assertEquals(l.getCountry(),"FI");
		assertEquals(l.getKey(),"00000.2.02934");
	}
	
	@Test
	public void testForecast() throws Exception {
		String fName = "forecast.xml";
		Document xml = parseXmlFile(DIR+fName); 
		ForecastResponse fr = accessor.decodeWUGForecast(xml);
		List<ForecastDay> days = fr.getForecastDays();
		assertNotEquals(days.size(), 0);
		ForecastDay fd = days.get(0);
		assertEquals("39",fd.getMaxC());
		assertEquals("19",fd.getMinC());
		assertEquals("7/Feb/2018",fd.getDate());
		assertEquals("6",fd.getAverageWindKmh());
	}	

}