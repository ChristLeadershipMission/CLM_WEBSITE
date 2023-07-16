package worldwide.clm.clmwebsite.config.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import worldwide.clm.clmwebsite.config.mail.MailConfiguration;

@Configuration
public class ApplicationConfiguration {
	
	
	@Value("${cloudinary.api.secret}")
	private String apiSecret;
	@Value("${mail.api.key}")
	private String mailApiKey;
	@Value("${sendinblue.mail.url}")
	private String mailUrl;
	
	
	@Bean
	public MailConfiguration mailConfig(){
		return new MailConfiguration (mailApiKey, mailUrl);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder ();
	}
}
