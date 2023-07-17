package worldwide.clm.clmwebsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.service.AuthService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static worldwide.clm.clmwebsite.common.Message.CREATED;

@SpringBootTest
class AuthServiceImplTest {
	
	@Autowired
	private AuthService service;
	
	@Test
	void signup() {
		SignupRequest request = new SignupRequest ();
		request.setEmail ("email@gmail.com");
		request.setPassword ("password");
		request.setFullName ("fullname lastname");
		var result = service.signup (request);
		assertThat(result).isNotNull ();
		assertThat(result.getMessage ()).isEqualTo (CREATED);
		assertThat (result.isSuccess ()).isTrue ();
	}
}