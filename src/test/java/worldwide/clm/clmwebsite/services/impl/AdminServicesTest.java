package worldwide.clm.clmwebsite.services.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.dto.request.AdminInvitationRequest;
import worldwide.clm.clmwebsite.dto.request.AdminSignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.adminServices.AdminService;
import worldwide.clm.clmwebsite.services.authenticationServices.AuthenticationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static worldwide.clm.clmwebsite.common.Message.CREATED;

@SpringBootTest
class AdminServicesTest {
	
	@Autowired
	private AuthenticationService service;

	@Autowired
	private AdminService adminService;

	@Test
	void sendAdminInvitationTest() throws ClmException {
		String email = "salaudeenoluwapelumi98@gmail.com";
		ApiResponse apiResponse = adminService.sendInvitationLink(
				AdminInvitationRequest.builder()
						.emailAddress(email)
						.firstName("Olamilekan")
						.lastName("Boy")
						.phoneNumber("08089649909")
						.build()
		);
		assertNotNull(apiResponse);
	}

}