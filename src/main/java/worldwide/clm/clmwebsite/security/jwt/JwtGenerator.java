package worldwide.clm.clmwebsite.security.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import worldwide.clm.clmwebsite.security.SecurityConstants;
import worldwide.clm.clmwebsite.security.user.SecureUser;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.time.Instant.now;
import static worldwide.clm.clmwebsite.utils.AppUtils.EMAIL_VALUE;

@Component
@RequiredArgsConstructor
public class JwtGenerator {
    private static final SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(Authentication authentication, Long expiration) {
        SecureUser authenticatedUser = (SecureUser) authentication.getPrincipal();
        String username = authenticatedUser.getUsername();
        return generateToken(username, expiration);
    }
    public String generateToken(String username, Long expiration) {
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expiration);

        return JWT.create()
                .withIssuedAt(now())
                .withExpiresAt(now().plusSeconds(172800L))
                .withClaim(EMAIL_VALUE, username)
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
    
    public static String generateVerificationToken() {
        System.out.println(new SecurityConstants().getJwtSecret());
        return Jwts.builder()
                .setIssuer("CLM")
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getSignature();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new AuthenticationCredentialsNotFoundException("Invalid token");
        }
    }
    
   
}