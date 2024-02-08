package worldwide.clm.clmwebsite.services.authenticationServices;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.dto.request.ChangePasswordRequest;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.request.Recipient;
import worldwide.clm.clmwebsite.dto.request.ResetPasswordRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.BioDataResponse;
import worldwide.clm.clmwebsite.exception.ClmAuthenticationException;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.PasswordMismatchException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.bioDataServices.BioDataService;
import worldwide.clm.clmwebsite.services.notificationServices.mailServices.MailService;
import worldwide.clm.clmwebsite.utils.JwtUtility;

import java.util.List;

import static worldwide.clm.clmwebsite.common.Message.PASSWORDS_DO_NOT_MATCH;
import static worldwide.clm.clmwebsite.utils.AppUtils.*;
import static worldwide.clm.clmwebsite.utils.HtmlFileUtility.getFileTemplateFromClasspath;
import static worldwide.clm.clmwebsite.utils.ResponseUtils.mailResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final BioDataService bioDataService;
    private final JwtUtility jwtUtility;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ApiResponse sendPasswordResetLink(String emailAddress) throws ClmException, MessagingException {
        BioDataResponse foundBioData = bioDataService.findByEmail(emailAddress);
        String passwordResetLink = generatePasswordResetLink(emailAddress);
        sendPasswordResetLinkMail(emailAddress, passwordResetLink, foundBioData.getFirstName());
        return mailResponse();
    }

    @Override
    public ApiResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws ClmAuthenticationException, UserNotFoundException {
        String email = validateToken(resetPasswordRequest.getEncryptedEmail());
        System.out.println(email);
        return changePassword(email, resetPasswordRequest.getNewPassword());
    }

    private ApiResponse changePassword(String email, String newPassword) throws UserNotFoundException {
        String encryptedPassword = passwordEncoder.encode(newPassword);
        return bioDataService.resetPassword(email, encryptedPassword);
    }

    @Override
    public ApiResponse changePassword(ChangePasswordRequest changePasswordRequest) throws UserNotFoundException, PasswordMismatchException {
        if (!bioDataService.passwordMatch(changePasswordRequest.getEmailAddress(), changePasswordRequest.getOldPassword())){
            throw new PasswordMismatchException(PASSWORDS_DO_NOT_MATCH);
        }
        return changePassword(changePasswordRequest.getEmailAddress(), changePasswordRequest.getNewPassword());
    }

    private String validateToken(String encryptedEmail) throws ClmAuthenticationException {
        return jwtUtility.extractClaimFrom(encryptedEmail, EMAIL_VALUE).asString();
    }

    private void sendPasswordResetLinkMail(String emailAddress, String passwordResetLink, String firstName) throws MessagingException, ClmException {
        String htmlTemplate = getFileTemplateFromClasspath(RESET_PASSWORD_HTML_TEMPLATE_LOCATION);
        htmlTemplate = String.format(htmlTemplate, firstName, passwordResetLink);
        EmailNotificationRequest notificationRequest = EmailNotificationRequest.builder()
                .to(List.of(new Recipient(emailAddress)))
                .subject(PASSWORD_RESET_LINK)
                .text(htmlTemplate)
                .build();
        mailService.sendHtmlMail(notificationRequest);
    }

    private String generatePasswordResetLink(String emailAddress) {
        String token = jwtUtility.generateEncryptedLink(emailAddress);
        return CLIENT_BASE_URL+token;
    }
}
