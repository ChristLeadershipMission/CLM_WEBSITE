package worldwide.clm.clmwebsite.utils;

import lombok.AllArgsConstructor;
import worldwide.clm.clmwebsite.exception.ClmException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AppUtils {
    public static final String EMAIL_VALUE = "email";
    public static final String USERNAME_VALUE = "username";
    public static final String CLM_VALUE = "username";
    public static final String CLAIM_VALUE = "claim";
    public static final String ROLES_VALUE = "roles";
    public static final String USER = "user";
    public static final String ERROR_VALUE = "error";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN_VALUE = "access_token";
    public static final String ONBOARDING_MAIL_SUBJECT = "Email Verification";
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
    private static final String USER_VERIFICATION_BASE_URL = "http://localhost:8080";
    public static final String ADMIN_REGISTRATION_PAGE_URL = "http://localhost:3000/admin/registration";
    public static final String MAIL_TEMPLATE_LOCATION = "C:\\Users\\oguns\\Others\\NewFolder\\OpenFactor\\CodeBase\\CLM_WEBSITE\\src\\main\\resources\\adminInvitationTemplate.txt";
    public static final String EMPTY_SPACE_VALUE = "";
    public static final String ADMIN_INVITATION_MAIL_TEMPLATE = """
            Calvary Greetings, 
            <br>
            <br>			
            You've been selected as an admin for the CLM website. Your expertise will play a crucial role in shaping our platform.
            <br>
            Kindly click the registration button below to get started and start managing with impact.
			<br>
			<br>		
            Thank you for being a part of our team.
            """;
    public static final String CLM_WEBSITE_ADMIN_INVITATION = "CLM-WEBSITE ADMIN INVITATION";
    public static final String LOGIN_ENDPOINT = "/clmWebsite/api/v1/login";
    public static final String ADMIN_REGISTRATION_ENDPOINT = "/clmWebsite/api/v1/admin/registration";

    public static final String WELCOME_MAIL_TEMPLATE_LOCATION = "src/main/resources/templates/welcome.html";

    public static List<String> getAuthWhiteList() {
        return List.of(
                ADMIN_REGISTRATION_ENDPOINT, LOGIN_ENDPOINT
        );
    }

    public static String getMailTemplate() throws ClmException {
        try (BufferedReader reader = new BufferedReader(new FileReader(
                WELCOME_MAIL_TEMPLATE_LOCATION))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException exception) {
            throw new ClmException(exception.getMessage());
        }
    }

}
