package worldwide.clm.clmwebsite.service;

import jakarta.validation.constraints.NotNull;
import worldwide.clm.clmwebsite.dto.request.LoginRequest;
import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.TokenResponseDto;
import worldwide.clm.clmwebsite.exception.InvalidLoginDetailsException;

public interface AuthService {
	
	ApiResponse signup(SignupRequest request);
	
	TokenResponseDto userLogin(@NotNull LoginRequest request) throws InvalidLoginDetailsException;
	
	ApiResponse verifyAccount(long userId, String token);
}
