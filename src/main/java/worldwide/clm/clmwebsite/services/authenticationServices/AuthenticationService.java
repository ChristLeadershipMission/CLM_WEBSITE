package worldwide.clm.clmwebsite.services.authenticationServices;

import jakarta.mail.MessagingException;
import worldwide.clm.clmwebsite.dto.request.ChangePasswordRequest;
import worldwide.clm.clmwebsite.dto.request.ResetPasswordRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.AuthenticationException;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.PasswordMismatchException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

public interface AuthenticationService {


    ApiResponse sendPasswordResetLink(String emailAddress) throws ClmException, MessagingException;

    ApiResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws AuthenticationException, UserNotFoundException;

    ApiResponse changePassword(ChangePasswordRequest changePasswordRequest) throws UserNotFoundException, PasswordMismatchException;
}
