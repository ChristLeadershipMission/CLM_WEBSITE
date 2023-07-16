package worldwide.clm.clmwebsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.config.security.jwt.JwtGenerator;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.request.Recipient;
import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.service.AuthService;
import worldwide.clm.clmwebsite.service.MemberService;
import worldwide.clm.clmwebsite.utils.AppUtils;

import static worldwide.clm.clmwebsite.common.Message.EMAIL_ALREADY_EXIST;
import static worldwide.clm.clmwebsite.utils.AppUtils.getMailTemplate;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final MemberService service;
	
	
	@Override
	public ApiResponse signup(SignupRequest request) {
		Member findMember = service.findMemberByEmail(request.getEmail());
		if (findMember != null) {
			throw new UserAlreadyExistsException (EMAIL_ALREADY_EXIST);
		}
		Member createMember = Member.builder()
				.fullName (request.getFullName ())
				.email (request.getEmail ())
				.password (request.getPassword ())
				.build();
		var savedMember = service.saveMembers (createMember);
		EmailNotificationRequest notificationRequest =
				buildNotificationRequest(savedMember.getEmail (), savedMember.getFullName(), savedMember.getId());
		return null;
	}
	
	private EmailNotificationRequest buildNotificationRequest(String email, String fullName, Long id) {
		EmailNotificationRequest request = new EmailNotificationRequest();
		request.getTo().add(new Recipient (fullName, email));
		String template = getMailTemplate();
		String content = String.format (template, fullName, AppUtils.generateVerificationToken(id));
		request.setHtmlContent (content);
		return request;
	}
}
