package me.bdan.raincheck.frontend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class JwtAuthenticationResponse {
	private boolean success;
	private String token;
	private String msg;
	private long expires;
}
