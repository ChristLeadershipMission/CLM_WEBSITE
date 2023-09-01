package worldwide.clm.clmwebsite.services.authenticationServices;

import worldwide.clm.clmwebsite.dto.request.AdminSignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

public interface AuthenticationService {
	
	ApiResponse signup(AdminSignupRequest request) throws UserAlreadyExistsException, UserNotFoundException;

}
