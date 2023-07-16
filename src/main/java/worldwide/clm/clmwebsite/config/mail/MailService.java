package worldwide.clm.clmwebsite.config.mail;


import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;

public interface MailService {
	
	String sendHtmlMail(EmailNotificationRequest emailNotificationRequest);
}
