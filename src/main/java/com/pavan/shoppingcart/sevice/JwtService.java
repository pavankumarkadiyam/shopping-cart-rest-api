package com.pavan.shoppingcart.sevice;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.pavan.shoppingcart.security.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	
	private final String key = "89084A8D1BEAEB7DEAC1056FB48CBCAE7F95FC5DA5A5BDE2F4C815F7BB23C41AF85773B28681F6321CFC549996388EA6EFD4748F723846FB91E07297489F3617";
	private final static long VALIDITY = TimeUnit.MINUTES.toMillis(30);
	
	public String generateToken(CustomUserDetails userDetails) {
		
		return Jwts.builder()
					.subject(userDetails.getUsername())
					.issuedAt(Date.from(Instant.now()))
					.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
					.signWith(generateKey())
					.compact();
	}
	
	public SecretKey generateKey() {
		byte [] decodedKey = Base64.getDecoder().decode(key);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	
	public Claims getClaims(String token) {
		return Jwts.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	
	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public boolean isTokenValid(String token) {
		return getClaims(token).getExpiration().after(Date.from(Instant.now()));
	}
}
