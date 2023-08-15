package worldwide.clm.clmwebsite.services.mailServices;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.utils.AppUtils;

import static worldwide.clm.clmwebsite.utils.AppUtils.getMailTemplate;
import static worldwide.clm.clmwebsite.utils.ResponseUtils.getOnboardingMailMessage;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
	private final JavaMailSender mailSender;


	@Override
	public ApiResponse sendOnboardingMail(EmailNotificationRequest emailNotificationRequest, Long id) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(emailNotificationRequest.getTo().get(0).getEmail());
		mailMessage.setFrom(emailNotificationRequest.getSender());
		mailMessage.setSubject(emailNotificationRequest.getSubject());
		String template = getMailTemplate();
		String firstName = emailNotificationRequest.getTo().get(0).getFirstName();
		String content = String.format (template, firstName, AppUtils.generateVerificationToken(id));
		mailMessage.setText(content);
		mailSender.send(mailMessage);
		return getOnboardingMailMessage();
	}
}
