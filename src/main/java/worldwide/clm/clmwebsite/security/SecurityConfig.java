package worldwide.clm.clmwebsite.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import worldwide.clm.clmwebsite.security.filters.ClmAuthenticationFilter;
import worldwide.clm.clmwebsite.security.filters.ClmAuthorizationFilter;
import worldwide.clm.clmwebsite.services.adminServices.AdminService;
import worldwide.clm.clmwebsite.services.bioDataServices.BioDataService;
import worldwide.clm.clmwebsite.services.memberServices.MemberService;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;
import worldwide.clm.clmwebsite.utils.JwtUtility;
import worldwide.clm.clmwebsite.utils.WhiteList;

import static worldwide.clm.clmwebsite.utils.AppUtils.LOGIN_ENDPOINT;
import static worldwide.clm.clmwebsite.utils.AppUtils.getAuthWhiteList;

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
                .authorizeHttpRequests(c->c
                        .requestMatchers(getAuthWhiteList())
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .build();
    }
    @Bean
    public WebMvcConfigurer configurer(){
      return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(@NonNull CorsRegistry registry) {
          registry.addMapping("/**")
                  .allowedOrigins("*")
                  .allowedMethods("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS");
        }
      };
    }
}
