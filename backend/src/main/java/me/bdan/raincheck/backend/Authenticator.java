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

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import me.bdan.raincheck.backend.data.AuthenticationResponse;

public class Authenticator {
	Map<String, String> users = new HashMap<>();
	
	String SECRET;
	long TIMEOUT = 1000 * 3600 * 1;

	public Authenticator(String secret) {
		this.SECRET = secret;
		users.put("frontend", "fe");
	}

	public AuthenticationResponse authenticate(String credentials) {
    	String decoded = new String(Base64.getDecoder().decode(credentials));
    	String[] pcs = decoded.split(":");
    	if (pcs.length!=2) {
    		return new AuthenticationResponse(false,"","Malformed credentials",0);
    	}
    	if (pcs[1].equals(users.get(pcs[0]))) {
    		long expires = System.currentTimeMillis() + TIMEOUT;
    		String token = Jwts.builder()
                    .setSubject(pcs[0])
                    .setExpiration(new Date(expires))
                    .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                    .compact();
    		return new AuthenticationResponse(true,token,"OK",expires);
    	} else {
    		return new AuthenticationResponse(false,"","Incorrect credentials",0);
    	}
	}
}
