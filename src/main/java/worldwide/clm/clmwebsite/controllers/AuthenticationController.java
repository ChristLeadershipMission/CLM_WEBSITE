package worldwide.clm.clmwebsite.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.LoginRequest;
import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.TokenResponseDto;
import worldwide.clm.clmwebsite.exception.InvalidLoginDetailsException;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.services.authenticationServices.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService service;
	
<<<<<<< HEAD:src/main/java/worldwide/clm/clmwebsite/controllers/AuthenticationController.java
	@PostMapping("register")
	public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SignupRequest request) throws UserAlreadyExistsException {
		return new ResponseEntity<> (service.signup(request), HttpStatus.CREATED);
=======
	@PostMapping("")
	public ResponseEntity<ApiResponse> sigUp(@Valid @RequestBody SignupRequest request){
		return new ResponseEntity<> (service.signup (request), HttpStatus.CREATED);
>>>>>>> b6754370e678906f5c35754b17629b704bd9072e:src/main/java/worldwide/clm/clmwebsite/controllers/AuthController.java
	}
	
	@PostMapping("login")
	public ResponseEntity<TokenResponseDto> signIn(@Valid @RequestBody LoginRequest request) throws InvalidLoginDetailsException {
		return new ResponseEntity<>(service.userLogin(request), HttpStatus.OK);
	}
	
	@PostMapping("verify/{userId}/{token}")
	public ResponseEntity<ApiResponse> verify(@PathVariable Long userId, @PathVariable String token){
		return new ResponseEntity<>(service.verifyAccount (userId, token),
				HttpStatus.OK);
	}
}
