package worldwide.clm.clmwebsite.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import worldwide.clm.clmwebsite.exception.ClmAuthenticationException;

import java.security.SecureRandom;
import java.util.*;

import static java.time.Instant.now;
import static worldwide.clm.clmwebsite.common.Message.INVALID_TOKEN;
import static worldwide.clm.clmwebsite.utils.AppUtils.*;

@AllArgsConstructor
public class JwtUtility {
    private final String secret;

    public String generateAccessToken(Collection<? extends GrantedAuthority> authorities){
        Map<String, String> map = new HashMap<>();
        int count = 1;
        for (GrantedAuthority authority:authorities) {
            map.put(CLAIM_VALUE+count, authority.getAuthority());
            count++;
        }
        return JWT.create()
                .withIssuedAt(now())
                .withExpiresAt(now().plusSeconds(12000L))
                .withClaim(ROLES_VALUE, map)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String generateEncryptedLink(String userEmail) {
        return JWT.create()
                .withIssuedAt(now())
                .withExpiresAt(now().plusSeconds(315360000000L))
                .withClaim(EMAIL_VALUE, userEmail)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public Map<String, Claim> extractClaimsFrom(String token) throws ClmAuthenticationException {
        DecodedJWT decodedJwt = validateToken(token);
        if (decodedJwt.getClaim(ROLES_VALUE)==null) throw new ClmAuthenticationException(INVALID_TOKEN);
        return decodedJwt.getClaims();
    }
    public Claim extractClaimFrom(String token, String key) throws ClmAuthenticationException {
        DecodedJWT decodedJwt = validateToken(token);
        if (decodedJwt.getClaim(key)==null) throw new ClmAuthenticationException(INVALID_TOKEN);
        return decodedJwt.getClaim(key);
    }

    public DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret))
                .build().verify(token);
    }

    public String generateEncryptedLink(Map<String, Object> requestAsMap) {
        return JWT.create()
                .withIssuedAt(now())
                .withExpiresAt(now().plusSeconds(172800L))
                .withClaim(ADMIN, requestAsMap)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }
    public String generateAdminDefaultPassword(String email) {
        String[] specialChars = new String[]{"!", "?", "@", "#", "$", "%", "^", "&", "*", "/", "{", "}", ";", ":","<", ">"};
        SecureRandom random = new SecureRandom();
        List<String> emailAsList = List.of(email.split("@")[0].split(""));
        emailAsList = emailAsList.stream().filter(each -> !Objects.equals(each, ".")).toList();
        String defaultPassword = "";
        int emailLength = emailAsList.size();
        for (int i = 0; i < 10; i++) {
            if (i == 4) defaultPassword = defaultPassword.concat(specialChars[random.nextInt(0, specialChars.length)]);
            else if (i == 7) defaultPassword = defaultPassword.concat(String.valueOf(random.nextInt(100, 999)));
            else {
                String letter = emailAsList.get(random.nextInt(0, emailLength));
                letter = i % 2 == 0 ? letter.toUpperCase() : letter.toLowerCase();
                defaultPassword = defaultPassword.concat(letter);
            }
        }
        return defaultPassword;
    }
}
