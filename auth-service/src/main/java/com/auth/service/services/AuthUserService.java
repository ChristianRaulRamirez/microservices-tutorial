package com.auth.service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.service.dto.AuthUserDTO;
import com.auth.service.dto.NewUserDTO;
import com.auth.service.dto.RequestDTO;
import com.auth.service.dto.TokenDTO;
import com.auth.service.entity.AuthUser;
import com.auth.service.repository.AuthUserRepository;
import com.auth.service.security.JwtProvider;

@Service
public class AuthUserService {

	@Autowired
	private AuthUserRepository authUserRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	public AuthUser save(NewUserDTO dto) {
		Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
		if(user.isPresent()) {
			return null;
		}
		String password = passwordEncoder.encode(dto.getPassword());
		AuthUser authUser = new AuthUser(dto.getUserName(),password,dto.getRole());
		return authUserRepository.save(authUser);
	}
	
	public TokenDTO login(AuthUserDTO dto) {
		Optional<AuthUser> user = authUserRepository.findByUserName(dto.getUserName());
		if(!user.isPresent()) {
			return null;
		}
		
		if(passwordEncoder.matches(dto.getPassword(), user.get().getPassword())) { //si coinciden las passwords
			return new TokenDTO(jwtProvider.createToken(user.get()));
		}
		return null;
	}

	public TokenDTO validate(String token,RequestDTO dto) {
		if(!jwtProvider.validate(token,dto)) {
			return null;
		}
		String username = jwtProvider.getUserNameFromToken(token);
		
		if(!authUserRepository.findByUserName(username).isPresent()) {
			return null;
		}
		
		return new TokenDTO(token);
	}
	
}









