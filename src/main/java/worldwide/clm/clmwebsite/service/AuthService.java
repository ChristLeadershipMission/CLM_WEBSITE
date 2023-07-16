package worldwide.clm.clmwebsite.service;

import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;

public interface AuthService {
	
	ApiResponse signup(SignupRequest request);
}
