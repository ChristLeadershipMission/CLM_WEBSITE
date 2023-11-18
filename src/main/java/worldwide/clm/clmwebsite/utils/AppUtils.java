package worldwide.clm.clmwebsite.utils;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import worldwide.clm.clmwebsite.exception.ClmException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AppUtils {
    public static final String DEFAULT_COORDINATING_MINISTER_ID = "clmwebsite.minister.defaultCoordinatingMinisterId";
    public static final String DEFAULT_CENTER_CAMPUS_ID = "clmwebsite.minister.defaultCenterCampusId";
    public static final String EMAIL_VALUE = "email";
    public static final String ADMIN = "admin";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String USERNAME_VALUE = "username";
    public static final String CLM_VALUE = "username";
    public static final String CLAIM_VALUE = "claim";
    public static final String ROLES_VALUE = "roles";
    public static final String USER = "user";
    public static final String ERROR_VALUE = "error";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN_VALUE = "access_token";
    public static final String ONBOARDING_MAIL_SUBJECT = "Email Verification";
    public static final String PASSWORD_RESET_LINK = "Password Reset Link";
    public static final String EMAIL_VERIFICATION_MAIL_TEMPLATE = """
            Dear %s,
                        
            Kindly click below link to verify your email and complete your registration
                        
            %s
            """;
    ;

    public static final String ADMIN_ONBOARDING_MESSAGE = """
            Dear %s,
            			
            Welcome to the CLM website admin portal! We're excited to have you on board. Your journey to efficiently manage and oversee the platform begins now.
            If you have any questions or need assistance, feel free to reach out.
            			
            Here's to a successful and collaborative experience ahead!
            """;
    public static final String LOCAL_SERVER_BASE_URL = "http://localhost:8080/";
    public static final String SERVER_BASE_URL = "https://clmwebsite.onrender.com/";
    public static final String CLIENT_BASE_URL = "http://localhost:3000/";
    public static final String INVITATION_ACCEPTANCE_VERIFICATION_URL = SERVER_BASE_URL +"clmWebsite/api/v1/admin/acceptInvitation/";
    public static final String ADMIN_INVITATION_HTML_TEMPLATE_LOCATION = "/adminInvitationTemplate.txt";
    public static final String SUCCESSFUL_REGISTRATION_HTML_TEMPLATE_LOCATION = "/successfulRegistrationTemplate.txt";
    public static final String RESET_PASSWORD_HTML_TEMPLATE_LOCATION = "/resetPasswordTemplate.txt";
    public static final String EMPTY_SPACE_VALUE = "";
    public static final String CLM_WEBSITE_ADMIN_INVITATION = "CLM-WEBSITE ADMIN INVITATION";
    public static final String LOGIN_ENDPOINT = "/clmWebsite/api/v1/login";
    public static final String INVITATION_ACCEPTANCE_VERIFICATION_ENDPOINT = "/clmWebsite/api/v1/admin/acceptInvitation/**";
    public static final String SEND_PASSWORD_RESET_LINK_ENDPOINT = "/clmWebsite/api/v1/auth/sendPasswordResetLink/**";
    public static final String RESET_PASSWORD_RESET_LINK_ENDPOINT = "/clmWebsite/api/v1/auth/resetPassword";
    public static final String ADMIN_REGISTRATION_ENDPOINT = "/clmWebsite/api/v1/admin/registration";
    public static final String ADMIN_INVITATION_ACCEPTANCE_ENDPOINT = "/clmWebsite/api/v1/admin/acceptInvitation/**";

    public static String[] getAuthWhiteList() {
        return new String[]{
                ADMIN_REGISTRATION_ENDPOINT, LOGIN_ENDPOINT, RESET_PASSWORD_RESET_LINK_ENDPOINT,
                SEND_PASSWORD_RESET_LINK_ENDPOINT,
                ADMIN_INVITATION_ACCEPTANCE_ENDPOINT,
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui/**",
                "webjars/**",
                "/swagger-ui.html"
        };
    }

    public static void main(String[] args) {

    }
}
