package worldwide.clm.clmwebsite.utils;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import worldwide.clm.clmwebsite.exception.BusinessLogicException;
import worldwide.clm.clmwebsite.exception.InvalidLoginDetailsException;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

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
    public ResponseEntity<String> handleExceptions(BusinessLogicException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleExceptions(UserNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
