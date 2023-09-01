package worldwide.clm.clmwebsite.services.mailServices;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.utils.JwtUtility;

import static worldwide.clm.clmwebsite.utils.ResponseUtils.mailResponse;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
	private final JavaMailSender mailSender;
	private final JwtUtility jwtUtility;
	@Value("${spring.mail.username}")
	private String sender;


	@Override
	public ApiResponse sendMail(EmailNotificationRequest emailNotificationRequest) throws MessagingException {
		String email = emailNotificationRequest.getTo().get(0).getEmail();

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(email);
		helper.setFrom(sender);
		helper.setSubject(emailNotificationRequest.getSubject());
		helper.setText(emailNotificationRequest.getText(), true);

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(email);
		mailMessage.setFrom(sender);
		mailMessage.setSubject(emailNotificationRequest.getSubject());
		mailMessage.setText(emailNotificationRequest.getText());

		mailSender.send(message);
		return mailResponse();
	}
}
