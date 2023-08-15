package worldwide.clm.clmwebsite.utils;

import org.springframework.http.HttpStatus;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.TokenResponseDto;

import static worldwide.clm.clmwebsite.common.Message.*;

public class ResponseUtils {
	
	public static ApiResponse getFailureMessage() {
		return ApiResponse.builder()
				.message(REG_FAIL)
				.statusCode (HttpStatus.NO_CONTENT.value ())
				.success (true)
				.build();
	}
	
	public static ApiResponse getCreatedMessage(){
		return ApiResponse.builder()
				.message (CREATED)
				.success (true)
				.statusCode (HttpStatus.CREATED.value ())
				.build();
	}

	public static ApiResponse getOnboardingMailMessage(){
		return ApiResponse.builder()
				.message (VERIFICATION_MAIL_SENT)
				.success (true)
				.statusCode (HttpStatus.CREATED.value ())
				.build();
	}
	
	public static TokenResponseDto getLoginResponse(String token, String refreshToken) {
		return TokenResponseDto.builder()
				.accessToken("Bearer "+ token)
				.refreshToken("Bearer "+ refreshToken)
				.build();
	}
}