package com.auth.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.service.dto.AuthUserDTO;
import com.auth.service.dto.TokenDTO;
import com.auth.service.entity.AuthUser;
import com.auth.service.services.AuthUserService;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

	@Autowired
	private AuthUserService authUserService; 
	
	@PostMapping("/login")
	public ResponseEntity<TokenDTO> login(@RequestBody AuthUserDTO authUserDTO){
		TokenDTO tokenDTO = authUserService.login(authUserDTO);
		if(tokenDTO == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(tokenDTO);
	}
	
	@PostMapping("/validate")
	public ResponseEntity<TokenDTO> validate(@RequestParam String token){
		TokenDTO tokenDTO = authUserService.validate(token);
		if(tokenDTO == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(tokenDTO);
	}
	
	@PostMapping("/create")
	public ResponseEntity<AuthUser> create(@RequestBody AuthUserDTO authUserDTO){
		AuthUser authUser = authUserService.save(authUserDTO);
		if(authUser == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(authUser);
	}
}
