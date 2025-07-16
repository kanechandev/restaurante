package com.kanechan.restaurante.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanechan.restaurante.dto.AuthRequest;
import com.kanechan.restaurante.dto.AuthResponse;
import com.kanechan.restaurante.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							request.getUsername(),
							request.getPassword()));
			User user = (User) authentication.getPrincipal();
			String token = jwtService.generateToken(user.getUsername());
			
			return ResponseEntity.ok(new AuthResponse(token));
		} catch (BadCredentialsException ex){
			return ResponseEntity
					.status(401)
					.body(new AuthResponse("Credenciais inválidas"));
		}
	}
}