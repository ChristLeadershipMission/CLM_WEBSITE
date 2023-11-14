package worldwide.clm.clmwebsite.utils;

import org.springframework.http.HttpStatus;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.TokenResponseDto;

import static worldwide.clm.clmwebsite.common.Message.*;

public class ResponseUtils {
	
	public static ApiResponse getDuplicateCampusesMessage() {
		return ApiResponse.builder()
				.message(CAMPUS_ALREADY_EXISTS)
				.statusCode (HttpStatus.BAD_REQUEST.value())
				.success (true)
				.build();
	}
	public static ApiResponse getCampusNotFoundMessage() {
		return ApiResponse.builder()
				.message(CAMPUS_NOT_FOUND)
				.statusCode (HttpStatus.BAD_REQUEST.value())
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

	public static ApiResponse mailResponse(){
		return ApiResponse.builder()
				.message (MAIL_HAS_BEEN_SENT_SUCCESSFULLY)
				.success (true)
				.statusCode (HttpStatus.OK.value ())
				.build();
	}
	
	public static TokenResponseDto getLoginResponse(String token, String refreshToken) {
		return TokenResponseDto.builder()
				.accessToken("Bearer "+ token)
				.refreshToken("Bearer "+ refreshToken)
				.build();
	}
	
	public static ApiResponse okResponse(){
		return ApiResponse.builder()
				.statusCode(HttpStatus.OK.value())
				.success (true)
				.message ("Verified")
				.build();
	}
	public static ApiResponse passwordResetResponse(){
		return ApiResponse.builder()
				.statusCode(HttpStatus.OK.value())
				.success (true)
				.message ("Password has been reset successfully")
				.build();
	}
	public static ApiResponse alreadyCreated(String message){
		return ApiResponse.builder()
				.message(message)
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.success(false)
				.build();
	}

	public static ApiResponse created(Object message) {
		return ApiResponse.builder()
				.message((String) message)
				.statusCode(HttpStatus.CREATED.value())
				.success(true)
				.build();
	}

	public static ApiResponse noEventFound(String message) {
		return ApiResponse.builder()
				.message(message)
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.success(false)
				.build();
	}

	public static ApiResponse eventDeleted(String message) {
		return ApiResponse.builder()
				.message(message)
				.statusCode(HttpStatus.OK.value())
				.success(true)
				.build();

	}

	public static ApiResponse updated(String message) {
		return ApiResponse.builder()
				.message(message)
				.statusCode(HttpStatus.OK.value())
				.success(true)
				.build();
	}
}
