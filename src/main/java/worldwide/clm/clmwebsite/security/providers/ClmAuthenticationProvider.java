package worldwide.clm.clmwebsite.security.providers;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static worldwide.clm.clmwebsite.common.Message.AUTHENTICATION_FAILED_FOR_USER_WITH_EMAIL;
import static worldwide.clm.clmwebsite.common.Message.INVALID_EMAIL_OR_PASSWORD;

@Component
@AllArgsConstructor
public class ClmAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication authResult;
        String email = (String) authentication.getPrincipal();
        String password =  (String) authentication.getCredentials();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String userEmail = userDetails.getUsername();
        String userPassword = userDetails.getPassword();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        System.out.println("Password matches::>> "+passwordEncoder.matches(password, userPassword));
        if (passwordEncoder.matches(password, userPassword)){
            authResult = new UsernamePasswordAuthenticationToken(userEmail, userPassword, authorities);
            return  authResult;
        }
        throw new BadCredentialsException(INVALID_EMAIL_OR_PASSWORD);
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
