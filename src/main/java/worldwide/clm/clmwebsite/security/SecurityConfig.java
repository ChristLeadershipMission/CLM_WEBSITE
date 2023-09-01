package worldwide.clm.clmwebsite.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import worldwide.clm.clmwebsite.security.filters.ClmAuthenticationFilter;
import worldwide.clm.clmwebsite.security.filters.ClmAuthorizationFilter;
import worldwide.clm.clmwebsite.services.adminServices.AdminService;
import worldwide.clm.clmwebsite.services.bioDataServices.BioDataService;
import worldwide.clm.clmwebsite.services.memberServices.MemberService;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;
import worldwide.clm.clmwebsite.utils.JwtUtility;

import static org.springframework.http.HttpMethod.POST;
import static worldwide.clm.clmwebsite.utils.AppUtils.*;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
  private final AuthenticationManager authenticationManager;
  private final JwtUtility jwtUtil;
  private final ObjectMapper mapper = new ObjectMapper();
  private final BioDataService bioDataService;
  private final MemberService memberService;
  private final AdminService adminService;
  private final MinisterService ministerService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      UsernamePasswordAuthenticationFilter authenticationFilter = new ClmAuthenticationFilter(
              authenticationManager, jwtUtil, bioDataService, memberService, adminService, ministerService
      );;
      authenticationFilter.setFilterProcessesUrl(LOGIN_ENDPOINT);
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(c->c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new ClmAuthorizationFilter(jwtUtil), ClmAuthenticationFilter.class)
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(c->c.requestMatchers(POST, ADMIN_REGISTRATION_ENDPOINT)
                        .permitAll())
                .authorizeHttpRequests(c->c.requestMatchers(POST, LOGIN_ENDPOINT)
                        .permitAll())
                .authorizeHttpRequests(c->c.anyRequest().authenticated())
                .build();
    }
}
