package worldwide.clm.clmwebsite.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.services.authenticationServices.AuthenticationService;

import static org.assertj.core.api.Assertions.assertThat;
import static worldwide.clm.clmwebsite.common.Message.CREATED;

@SpringBootTest
class AuthenticationServiceImplTest {
	
	@Autowired
	private AuthenticationService service;
	
	@Test
	void signup() throws UserAlreadyExistsException {
		SignupRequest request = new SignupRequest ();
		request.setEmail ("ogunsmoyin.m@gmail.com");
		request.setPassword ("password");
		request.setFirstName("firstname");
		request.setLastName("lastname");
		var result = service.signup (request);
		System.out.println(result);
		assertThat(result).isNotNull ();
		assertThat(result.getMessage ()).isEqualTo (CREATED);
		assertThat (result.isSuccess ()).isTrue ();
	}
}