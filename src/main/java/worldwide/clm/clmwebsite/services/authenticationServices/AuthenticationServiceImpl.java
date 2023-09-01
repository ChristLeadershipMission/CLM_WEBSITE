package worldwide.clm.clmwebsite.services.authenticationServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Admin;
import worldwide.clm.clmwebsite.data.models.BioData;
import worldwide.clm.clmwebsite.dto.request.AdminSignupRequest;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.request.Recipient;
import worldwide.clm.clmwebsite.dto.response.AdminResponse;
import worldwide.clm.clmwebsite.services.adminServices.AdminService;
import worldwide.clm.clmwebsite.services.mailServices.MailService;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;

import java.util.List;

import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.memberServices.MemberService;

import static worldwide.clm.clmwebsite.common.Message.*;
import static worldwide.clm.clmwebsite.utils.AppUtils.ADMIN_ONBOARDING_MESSAGE;
import static worldwide.clm.clmwebsite.utils.AppUtils.ONBOARDING_MAIL_SUBJECT;
import static worldwide.clm.clmwebsite.utils.ResponseUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final MemberService memberService;
    private final AdminService adminService;
    private final MailService mailService;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;


    @Override
    public ApiResponse signup(AdminSignupRequest request) throws UserAlreadyExistsException, UserNotFoundException {
        AdminResponse foundAdmin = adminService.findByEmail(request.getEmail());
        boolean userIsAlreadyRegistered = foundAdmin != null;
        if (userIsAlreadyRegistered) throw new UserAlreadyExistsException(EMAIL_ALREADY_EXIST);
        AdminResponse registeredAdmin = registerAdmin(request);
        ApiResponse response = sendAdminOnboardingMail(registeredAdmin);
        if (response == null) return getFailureMessage();
        return getCreatedMessage();
    }

    private ApiResponse sendAdminOnboardingMail(AdminResponse admin) {
		String firstName = admin.getBioData().getFirstName();
		Recipient recipient = new Recipient(firstName, admin.getBioData().getEmailAddress());
		String content = String.format(ADMIN_ONBOARDING_MESSAGE, firstName);
		EmailNotificationRequest request = EmailNotificationRequest.builder()
                .to(List.of(recipient))
				.text(content)
                .subject(ONBOARDING_MAIL_SUBJECT)
                .build();
        try {
            return mailService.sendMail(request);
        }catch (Exception e){
            log.info("Admin Invitation {}", e.getMessage());
        }
        return null;
    }

    private AdminResponse registerAdmin(AdminSignupRequest request) {
        BioData bioData = BioData.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .emailAddress(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
        return adminService.register(Admin.builder().bioData(bioData).build());
    }

}
