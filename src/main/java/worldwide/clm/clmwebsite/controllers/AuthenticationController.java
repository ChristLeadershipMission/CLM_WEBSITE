package worldwide.clm.clmwebsite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Operation(
            summary = "Send Password Reset Link",
            description = "API for sending a password reset link to an email address."
    )
    @Parameter(
            name = "emailAddress",
            description = "The email address to which the reset link will be sent.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "string", format = "email")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Password reset link sent successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("sendPasswordResetLink/{emailAddress}")
    public ResponseEntity<ApiResponse> sendPasswordResetLink(@PathVariable String emailAddress) throws ClmException, MessagingException {
        return ResponseEntity.ok().body(authenticationService.sendPasswordResetLink(emailAddress));
    }
    @Operation(
            summary = "Reset Password",
            description = "API for resetting a user's password using a reset token."
    )
    @Parameter(
            name = "resetPasswordRequest",
            description = "Request containing the reset token and new password.",
            required = true,
            in = ParameterIn.PATH,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResetPasswordRequest.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Password reset successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )

    @PostMapping("resetPassword")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) throws ClmException, MessagingException {
        return ResponseEntity.ok().body(authenticationService.resetPassword(resetPasswordRequest));
    }

    @Parameter(
            name = "changePasswordRequest",
            description = "Request containing the user's current password and the new password.",
            required = true,
            in = ParameterIn.PATH,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ChangePasswordRequest.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Password change successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("changePassword")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) throws ClmException, MessagingException {
        return ResponseEntity.ok().body(authenticationService.changePassword(changePasswordRequest));
    }


}
