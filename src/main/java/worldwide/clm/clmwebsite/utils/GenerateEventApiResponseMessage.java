package worldwide.clm.clmwebsite.utils;


import org.springframework.http.HttpStatus;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;

public class GenerateEventApiResponseMessage {
    public static final String EVENT_ALREADY_CREATED = "An Event Already Exist With This Name";
    public static final String EVENT_CREATED_SUCCESSFULLY = "You have successfully created an event";
    public static final String NO_EVENT_FOUND = "Sorry, we couldn't find an event with such name";
    public static final String EVENT_UPDATED_SUCCESSFULLY ="Event has been updated successfully" ;
    public static final String EVENT_DELETED_SUCCESSFULLY = "Event deleted successfully";

    public static ApiResponse alreadyCreated(String message){
        return ApiResponse.builder()
                .message(message)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .success(false)
                .build();
    }

    public static ApiResponse created(String message) {
        return ApiResponse.builder()
                .message(message)
                .statusCode(HttpStatus.CREATED.value())
                .success(true)
                .build();
    }

    public static ApiResponse noEventFound(String message) {

        return ApiResponse.builder()
                .message(message)
                .statusCode(HttpStatus.NO_CONTENT.value())
                .success(false)
                .build();
    }

    public static ApiResponse updated(String message) {
        return ApiResponse.builder()
                .message(message)
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .build();
    }

    public static ApiResponse eventDeleted(String message) {
        return ApiResponse.builder()
                .message(message)
                .statusCode(HttpStatus.OK.value())
                .success(true)
                .build();

    }
}

