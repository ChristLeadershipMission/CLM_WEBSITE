package worldwide.clm.clmwebsite.controllers;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.ChangePasswordRequest;
import worldwide.clm.clmwebsite.dto.request.ResetPasswordRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.services.authenticationServices.AuthenticationService;

@RestController
@RequestMapping("/clmWebsite/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("sendPasswordResetLink/{emailAddress}")
    public ResponseEntity<ApiResponse> sendPasswordResetLink(@PathVariable String emailAddress) throws ClmException, MessagingException {
        return ResponseEntity.ok().body(authenticationService.sendPasswordResetLink(emailAddress));
    }

    @PostMapping("resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws ClmException, MessagingException {
        return ResponseEntity.ok().body(authenticationService.resetPassword(resetPasswordRequest));
    }

    @PostMapping("changePassword")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws ClmException, MessagingException {
        return ResponseEntity.ok().body(authenticationService.changePassword(changePasswordRequest));
    }


}
