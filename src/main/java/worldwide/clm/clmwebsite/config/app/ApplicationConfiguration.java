package worldwide.clm.clmwebsite.config.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import worldwide.clm.clmwebsite.config.mail.MailConfiguration;
import worldwide.clm.clmwebsite.security.jwt.JwtGenerator;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder ();
	}
}
