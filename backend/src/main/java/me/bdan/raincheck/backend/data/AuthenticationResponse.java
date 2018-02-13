package me.bdan.raincheck.backend.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponse {
	private boolean success;
	private String token;
	private String msg;
	private long expires=0;

	public AuthenticationResponse() {}

	public AuthenticationResponse(boolean success, String token, String msg) {
		this.success = success;
		this.token = token;
		this.msg = msg;
	}
	
	public AuthenticationResponse(boolean success, String token, String msg,long expires) {
		this(success,token,msg);
		this.expires=expires;
	}
	

}
