package worldwide.clm.clmwebsite.config;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import worldwide.clm.clmwebsite.utils.JwtUtility;

@Configuration
@Getter
public class BeanConfiguration {

	@Value("${jwt.signing.secret}")
	private String secret;
	@Value("${clm.website.support.server.baseurl}")
	private String clmWebsiteServerSupportBaseUrl;

	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder ();
	}

	@Bean
	public JwtUtility jwtUtility(){
		return new JwtUtility(secret);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	@Bean
	public WebClient webClient(){
		return WebClient.builder()
				.baseUrl(clmWebsiteServerSupportBaseUrl)
				.build();
	}

}
