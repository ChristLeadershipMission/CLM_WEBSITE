package worldwide.clm.clmwebsite.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import worldwide.clm.clmwebsite.dto.request.LoginRequest;
import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.TokenResponseDto;
import worldwide.clm.clmwebsite.services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;
	
	@PostMapping("register")
	public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SignupRequest request){
		return new ResponseEntity<> (service.signup(request), HttpStatus.CREATED);
	}
	
	@PostMapping("login")
	public ResponseEntity<TokenResponseDto> signIn(@Valid @RequestBody LoginRequest request){
		return new ResponseEntity<>(service.userLogin(request), HttpStatus.OK);
	}
}
