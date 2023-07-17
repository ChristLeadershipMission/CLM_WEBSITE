package worldwide.clm.clmwebsite.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.config.mail.MailService;
import worldwide.clm.clmwebsite.config.security.jwt.JwtGenerator;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.request.LoginRequest;
import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.TokenResponseDto;
import worldwide.clm.clmwebsite.exception.InvalidLoginDetailsException;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.service.AuthService;
import worldwide.clm.clmwebsite.service.MemberService;

import static worldwide.clm.clmwebsite.common.Message.*;
import static worldwide.clm.clmwebsite.utils.AppUtils.buildNotificationRequest;
import static worldwide.clm.clmwebsite.utils.ResponseUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
	private final MemberService service;
	private final MailService mailService;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authenticationManager;
	private final JwtGenerator generator;
	
	
	@Override
	public ApiResponse signup(SignupRequest request) {
		Member findMember = service.findMemberByEmail(request.getEmail());
		if (findMember != null) {
			throw new UserAlreadyExistsException (EMAIL_ALREADY_EXIST);
		}
		Member createMember = Member.builder()
				.fullName (request.getFullName ())
				.email (encoder.encode (request.getEmail ()))
				.password (request.getPassword ())
				.build();
		var savedMember = service.saveMembers (createMember);
		EmailNotificationRequest notificationRequest =
				buildNotificationRequest(savedMember.getEmail (), savedMember.getFullName(), savedMember.getId());
		String response = mailService.sendHtmlMail (notificationRequest);
		if(response == null) {
			return getFailureMessage();
		}
		return getCreatedMessage ();
	}
	
	@Override
	public TokenResponseDto userLogin(@NotNull LoginRequest request) throws InvalidLoginDetailsException {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken (request.getEmail(), request.getPassword()));
			String token = generator.generateToken(authentication, Long.valueOf(60000));
			String refreshToken = generator.generateToken(authentication, Long.valueOf(604800000));
			return getLoginResponse(token, refreshToken);
		}catch (Exception e) {
			throw new InvalidLoginDetailsException (LOGIN_FAIL);
		}
	}
	
	
	
	
}
