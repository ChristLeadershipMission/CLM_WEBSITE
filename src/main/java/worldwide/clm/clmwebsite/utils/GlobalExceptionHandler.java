package worldwide.clm.clmwebsite.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import worldwide.clm.clmwebsite.exception.*;

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

}
