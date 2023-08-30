//package worldwide.clm.clmwebsite.services.authenticationServices;
//
//import jakarta.validation.constraints.NotNull;
//import worldwide.clm.clmwebsite.dto.request.LoginRequest;
//import worldwide.clm.clmwebsite.dto.request.SignupRequest;
//import worldwide.clm.clmwebsite.dto.response.ApiResponse;
//import worldwide.clm.clmwebsite.dto.response.TokenResponseDto;
//import worldwide.clm.clmwebsite.exception.BusinessLogicException;
//import worldwide.clm.clmwebsite.exception.InvalidLoginDetailsException;
//import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
//
//public interface AuthenticationService {
//
//	ApiResponse signup(SignupRequest request) throws UserAlreadyExistsException;
//
//	TokenResponseDto userLogin(@NotNull LoginRequest request) throws InvalidLoginDetailsException;
//
//	ApiResponse verifyAccount(long userId, String token) throws BusinessLogicException;
//}
