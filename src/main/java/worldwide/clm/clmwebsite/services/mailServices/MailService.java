package worldwide.clm.clmwebsite.services.mailServices;


import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;

public interface MailService {

	ApiResponse sendOnboardingMail(EmailNotificationRequest emailNotificationRequest, Long id);
}
