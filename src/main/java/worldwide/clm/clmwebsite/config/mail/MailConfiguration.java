package worldwide.clm.clmwebsite.config.mail;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@AllArgsConstructor
@Getter
@Setter
@Configuration
public class MailConfiguration {

	@Bean
	public JavaMailSender javaMailSender(){
        return new JavaMailSenderImpl();
	}

}
