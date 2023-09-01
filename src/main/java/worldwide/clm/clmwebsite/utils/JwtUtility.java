package worldwide.clm.clmwebsite.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import worldwide.clm.clmwebsite.exception.AuthenticationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
                .withExpiresAt(now().plusSeconds(172800L))
                .withClaim(EMAIL_VALUE, userEmail)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public Map<String, Claim> extractClaimsFrom(String token) throws AuthenticationException {
        DecodedJWT decodedJwt = validateToken(token);
        if (decodedJwt.getClaim(ROLES_VALUE)==null) throw new AuthenticationException(INVALID_TOKEN);
        return decodedJwt.getClaims();
    }

    private DecodedJWT validateToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret))
                .build().verify(token);
    }

}
