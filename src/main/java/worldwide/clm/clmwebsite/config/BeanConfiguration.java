package worldwide.clm.clmwebsite.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import worldwide.clm.clmwebsite.utils.JwtUtility;

@Configuration
public class BeanConfiguration {

	@Value("${jwt.signing.secret}")
	private String secret;

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
}
