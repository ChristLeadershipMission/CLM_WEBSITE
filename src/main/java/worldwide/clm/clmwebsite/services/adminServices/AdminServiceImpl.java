package worldwide.clm.clmwebsite.services.adminServices;

import com.auth0.jwt.interfaces.Claim;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Admin;
import worldwide.clm.clmwebsite.data.models.BioData;
import worldwide.clm.clmwebsite.data.repositories.AdminRepository;
import worldwide.clm.clmwebsite.dto.request.AdminInvitationRequest;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.request.Recipient;
import worldwide.clm.clmwebsite.dto.response.AdminResponse;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.BioDataResponse;
import worldwide.clm.clmwebsite.exception.AuthenticationException;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.bioDataServices.BioDataService;
import worldwide.clm.clmwebsite.services.mailServices.MailService;
import worldwide.clm.clmwebsite.utils.JwtUtility;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static worldwide.clm.clmwebsite.common.Message.*;
import static worldwide.clm.clmwebsite.utils.AppUtils.*;

@Service
@AllArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService{
    private final AdminRepository adminRepository;
    private final BioDataService bioDataService;
    private final ModelMapper modelMapper;
    private final JwtUtility jwtUtility;
    private final MailService mailService;
    @Override
    public AdminResponse findByEmail(String emailAddress) throws UserNotFoundException {
        Admin foundAdmin = adminRepository.findByBioData_EmailAddress(emailAddress).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
        BioDataResponse bioDataResponse = modelMapper.map(foundAdmin.getBioData(), BioDataResponse.class);
        AdminResponse adminResponse = modelMapper.map(foundAdmin, AdminResponse.class);
        adminResponse.setBioData(bioDataResponse);
        return adminResponse;
    }

    @Override
    public AdminResponse register(Admin admin) {
        Admin registeredAdmin = adminRepository.save(admin);
        return modelMapper.map(registeredAdmin, AdminResponse.class);
    }

    @Override
    public ApiResponse sendInvitationLink(AdminInvitationRequest request) throws ClmException {
        validateDuplicateUserExistence(request.getEmailAddress());
        Map<String, Object> requestAsMap = buildRequestAsMap(request);
        String encryptedEmail = jwtUtility.generateEncryptedLink(requestAsMap);
        String invitationLink = INVITATION_ACCEPTANCE_VERIFICATION_URL.concat(encryptedEmail);
        Recipient recipient = Recipient.builder().email(request.getEmailAddress()).build();
        EmailNotificationRequest emailRequest = new EmailNotificationRequest();
        emailRequest.setTo(List.of(recipient));
        emailRequest.setSubject(CLM_WEBSITE_ADMIN_INVITATION);
        String template = getEmailTemplate();
        emailRequest.setText(String.format(template, invitationLink));
        try {
            mailService.sendMail(emailRequest);
        }catch (Exception e){
            log.info("Admin Invitation {}", e.getMessage());
        }
        return ResponseUtils.mailResponse();
    }

    @Override
    public String acceptInvitation(String encryptedLink) throws ClmException {
        Admin admin = getAdmin(encryptedLink);
        adminRepository.save(admin);
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(SUCCESSFUL_REGISTRATION_HTML_TEMPLATE_LOCATION))) {
            return reader.lines().collect(Collectors.joining());
        } catch (IOException exception) {
            throw new ClmException(FAILED_TO_GET_ACTIVATION_LINK);
        }
    }

    private Admin getAdmin(String encryptedLink) throws AuthenticationException {
        Claim claim = jwtUtility.extractClaimFrom(encryptedLink);
        Map<String, Object> adminAsMap = claim.asMap();
        BioData bioData = BioData.builder()
                .emailAddress((String) adminAsMap.get(EMAIL_VALUE))
                .firstName((String) adminAsMap.get(FIRST_NAME))
                .lastName((String) adminAsMap.get(LAST_NAME))
                .phoneNumber((String) adminAsMap.get(PHONE_NUMBER))
                .build();
        return Admin.builder().bioData(bioData).build();
    }


    private Map<String, Object> buildRequestAsMap(AdminInvitationRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put(EMAIL_VALUE, request.getEmailAddress());
        map.put(FIRST_NAME, request.getFirstName());
        map.put(LAST_NAME, request.getLastName());
        map.put(PHONE_NUMBER, request.getPhoneNumber());
        return map;
    }

    private String getEmailTemplate() throws ClmException {
        try(BufferedReader reader =
                    new BufferedReader(new FileReader(ADMIN_INVITATION_HTML_TEMPLATE_LOCATION))){
            return  reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new ClmException(FAILED_TO_GET_ACTIVATION_LINK);
        }
    }
    private void validateDuplicateUserExistence(String emailAddress) throws UserNotFoundException, UserAlreadyExistsException {
        bioDataService.validateDuplicateUserExistence(emailAddress);
    }
}
