package worldwide.clm.clmwebsite.services.mailServices;


import jakarta.mail.MessagingException;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;

public interface MailService {
	
	ApiResponse sendMail(EmailNotificationRequest emailNotificationRequest) throws MessagingException;
	ApiResponse sendHtmlMail(EmailNotificationRequest emailNotificationRequest) throws MessagingException;
}
