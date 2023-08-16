package worldwide.clm.clmwebsite.utils;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import worldwide.clm.clmwebsite.config.security.jwt.JwtGenerator;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.request.Recipient;
import worldwide.clm.clmwebsite.exception.BusinessLogicException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AppUtils {
	
	
	private static final String USER_VERIFICATION_BASE_URL="localhost:8080";
	public static  final String WELCOME_MAIL_TEMPLATE_LOCATION="src/main/resources/templates/welcome.html";
	
	public static String getMailTemplate(){
		try (BufferedReader reader = new BufferedReader(new FileReader (
				WELCOME_MAIL_TEMPLATE_LOCATION))){
			return reader.lines().collect(Collectors.joining());
		}catch (IOException exception){
			throw new BusinessLogicException (exception.getMessage());
		}
	}
	
	public static String generateVerificationToken(Long id) {
		return USER_VERIFICATION_BASE_URL+"?token="+JwtGenerator.generateVerificationTokenLogic (id);
	}
	
	public static EmailNotificationRequest buildNotificationRequest(String email, String fullName, Long id) {
		EmailNotificationRequest request = new EmailNotificationRequest();
		request.getTo().add(new Recipient (fullName, email));
		String template = getMailTemplate();
		String content = String.format (template, fullName, AppUtils.generateVerificationToken(id));
		request.setHtmlContent (content);
		return request;
	}
	
	public static boolean isValidToken(long userId, String token) {
		return Jwts.parserBuilder ()
				.require (token, userId)
				.build ()
				.isSigned (token);
	}
}
