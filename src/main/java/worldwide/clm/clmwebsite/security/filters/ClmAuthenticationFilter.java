package worldwide.clm.clmwebsite.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import worldwide.clm.clmwebsite.dto.request.LoginRequest;
import worldwide.clm.clmwebsite.dto.response.BioDataResponse;
import worldwide.clm.clmwebsite.enums.Role;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.adminServices.AdminService;
import worldwide.clm.clmwebsite.services.bioDataServices.BioDataService;
import worldwide.clm.clmwebsite.services.memberServices.MemberService;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;
import worldwide.clm.clmwebsite.utils.JwtUtility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static worldwide.clm.clmwebsite.common.Message.AUTHENTICATION_FAILED_FOR_USER_WITH_EMAIL;
import static worldwide.clm.clmwebsite.utils.AppUtils.*;


@Slf4j
@AllArgsConstructor
public class ClmAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtility jwtUtil;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BioDataService bioDataService;
    private final MemberService memberService;
    private final AdminService adminService;
    private final MinisterService ministerService;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email=EMPTY_SPACE_VALUE;
        try {
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);
            email = loginRequest.getEmail();
            String password = loginRequest.getPassword();
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
            System.out.println(email);
            Authentication authResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authResult);
            return authResult;
        }catch (IOException exception){
            throw new BadCredentialsException(String.format(AUTHENTICATION_FAILED_FOR_USER_WITH_EMAIL, email));
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        String accessToken = jwtUtil.generateAccessToken(authResult.getAuthorities());
        authResult.getDetails();
        Map<String, Object> responseData = new HashMap<>();
        responseData.put(ACCESS_TOKEN_VALUE, accessToken);
        String email = (String) authResult.getPrincipal();
        BioDataResponse foundBioData;
        try {
            foundBioData = bioDataService.findByEmail(email);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            Object foundUser = getActualUser(foundBioData);
            responseData.put(USER, foundUser);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());;
        }
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getOutputStream().write(mapper.writeValueAsBytes(
                responseData
        ));
    }

    private Object getActualUser(BioDataResponse foundBioData) throws UserNotFoundException {
        Object user = null;
        if (foundBioData.getRoles().contains(Role.MEMBER)){
            user = memberService.findByEmail(foundBioData.getEmailAddress());
        }else if (foundBioData.getRoles().contains(Role.MINISTER)){
            user = ministerService.findByEmail(foundBioData.getEmailAddress());
        }else if (foundBioData.getRoles().contains(Role.SUPER_ADMIN) || foundBioData.getRoles().contains(Role.ORDINARY_ADMIN)){
            user = adminService.findByEmail(foundBioData.getEmailAddress());
        }
        return user;
    }


}
