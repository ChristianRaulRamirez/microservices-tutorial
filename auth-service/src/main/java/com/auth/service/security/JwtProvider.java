package com.auth.service.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth.service.dto.RequestDTO;
import com.auth.service.entity.AuthUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	@Value("${jwt.secret}")
	private String secret;
	
	@Autowired
	private RouteValidator routeValidator;
	
	@PostConstruct
	protected void init() {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	public String createToken(AuthUser authUser) {
		Map<String, Object> claims = new HashMap<>();
		claims = Jwts.claims().setSubject(authUser.getUserName());
		claims.put("id", authUser.getId());
		claims.put("role", authUser.getRole());
		
		Date now =  new Date();
		Date exp = new Date(now.getTime() + 3600000);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public boolean validate(String token,RequestDTO requestDTO) {
		try {
			//parser es la inversa al builder 
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		}catch (Exception exception) {
			return false;
		}
		
		if(!isAdmin(token) && routeValidator.isAdminPath(requestDTO)) {
			return false;
		}
		return true;
	}
	
	private boolean isAdmin(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role").equals("admin");
	}
	
	public String getUserNameFromToken(String token) {
		try {
			return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
		}catch (Exception exception) {
			return "bad token";
		}
	}
}
