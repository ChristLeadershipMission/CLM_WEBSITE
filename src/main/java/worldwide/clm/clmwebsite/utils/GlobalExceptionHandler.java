package worldwide.clm.clmwebsite.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.*;

import static worldwide.clm.clmwebsite.common.Message.NO_EVENT_FOUND;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(UserAlreadyExistsException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(InvalidLoginDetailsException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(ClmException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(UserNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(AuthenticationException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleExceptions(CampusAlreadyExistsException e){
        ApiResponse apiResponse = ResponseUtils.getDuplicateCampusesMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleExceptions(CampusNotFoundException e){
        ApiResponse apiResponse = ResponseUtils.getCampusNotFoundMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleExceptions(EventNotFoundException e){
        ApiResponse apiResponse = ResponseUtils.noEventFound(NO_EVENT_FOUND);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleExceptions(DepartmentAlreadyExistsException e){
        ApiResponse apiResponse = ResponseUtils.alreadyCreated(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleExceptions(DepartmentNotFoundException e){
        ApiResponse apiResponse = ResponseUtils.alreadyCreated(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
