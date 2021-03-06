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

package me.bdan.raincheck.frontend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import me.bdan.raincheck.frontend.data.AuthorizationData;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorisationTests {
	
	@Autowired
	ApiController ac;
	@Autowired
	AuthorizationData ad;

	@Test
	public void authSuccess() {
		assertTrue(ac.authorize("frontend","fe"));
	}
	
	@Test
	public void authWrongCredentials() {
		assertFalse(ac.authorize("frontend","**"));
		assertEquals("Incorrect credentials",ad.getErr());
	}
}
