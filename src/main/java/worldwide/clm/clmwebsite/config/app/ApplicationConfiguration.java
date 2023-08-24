package worldwide.clm.clmwebsite.config.app;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import worldwide.clm.clmwebsite.config.mail.MailConfiguration;
import worldwide.clm.clmwebsite.security.jwt.JwtGenerator;

@Configuration
public class ApplicationConfiguration {
	
	@Value("${cloudinary.api.secret}")
	private String apiSecret;
	@Value("${cloudinary.api.key}")
	private String apiKey;
	@Value("${cloudinary.cloud.name}")
	private String cloudName;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder ();
	}
	
	@Bean
	public Cloudinary cloudinary(){
		return new Cloudinary(
				ObjectUtils.asMap(
						"cloud_name",cloudName,
						"api_key",apiKey,
						"api_secret",apiSecret
				)
		);
	}
}
