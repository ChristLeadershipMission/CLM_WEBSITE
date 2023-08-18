package worldwide.clm.clmwebsite.services.authenticationServices;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.dto.request.EmailNotificationRequest;
import worldwide.clm.clmwebsite.dto.request.Recipient;
import worldwide.clm.clmwebsite.services.mailServices.MailService;
import worldwide.clm.clmwebsite.security.jwt.JwtGenerator;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.dto.request.LoginRequest;
import worldwide.clm.clmwebsite.dto.request.SignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.TokenResponseDto;
import worldwide.clm.clmwebsite.exception.BusinessLogicException;
import worldwide.clm.clmwebsite.exception.InvalidLoginDetailsException;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;

import java.util.List;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.memberServices.MemberService;
import worldwide.clm.clmwebsite.utils.AppUtils;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.util.Optional;

import static worldwide.clm.clmwebsite.common.Message.*;
import static worldwide.clm.clmwebsite.utils.AppUtils.ONBOARDING_MAIL_SUBJECT;
import static worldwide.clm.clmwebsite.utils.ResponseUtils.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
	private final MemberService service;
	private final MailService mailService;
	private final PasswordEncoder encoder;
	private final AuthenticationManager authenticationManager;
	private final JwtGenerator generator;
	
	
	@Override
	public ApiResponse signup(SignupRequest request) throws UserAlreadyExistsException {
        Member foundMember = service.findMemberByEmail(request.getEmail());
        boolean userIsAVerifiedExistingMember = foundMember != null && foundMember.isEnabled();
        boolean userIsARegisteredMember = foundMember != null;
        if (userIsAVerifiedExistingMember) throw new UserAlreadyExistsException(EMAIL_ALREADY_EXIST);
        Member registeredMember = foundMember;
        if (!userIsARegisteredMember) {
            registeredMember = registerMember(request);
        }
        ApiResponse response = sendOnboardingMailTo(registeredMember);
        if (response == null) return getFailureMessage();
        return getCreatedMessage();
    }

	private ApiResponse sendOnboardingMailTo(Member registeredMember) {
		Recipient recipient = new Recipient(registeredMember.getFirstName(), registeredMember.getEmail());
		EmailNotificationRequest request = EmailNotificationRequest.builder()
				.to(List.of(recipient))
				.subject(ONBOARDING_MAIL_SUBJECT)
				.build();
        return mailService.sendOnboardingMail(request, registeredMember.getId());
	}

	private Member registerMember(SignupRequest request) {
		Member member = Member.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.password(encoder.encode (request.getPassword()))
				.build();
        return service.register(member);
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
	
	@Override
	public ApiResponse verifyAccount(long userId, String token) throws BusinessLogicException {
		if (AppUtils.isValidToken(userId,token)) return getVerifiedResponse(userId);
		throw new BusinessLogicException (ACC_VERIFY_FAILURE);
	}
	
	private ApiResponse getVerifiedResponse(Long userId) throws UserNotFoundException {
		Optional<Member> foundUser = service.findMemberById(userId);
		if (foundUser.isEmpty ()){
			throw new UserNotFoundException (USER_NOT_FOUND);
		}
		foundUser.ifPresent (this::enableMemberAccount);
		return ResponseUtils.okResponse (foundUser);
	}
	
	private void enableMemberAccount(Member member) {
		member.setEnabled (true);
		service.saveMembers (member);
	}
	
	
}
