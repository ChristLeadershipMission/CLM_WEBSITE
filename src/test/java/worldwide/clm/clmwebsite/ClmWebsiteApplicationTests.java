package worldwide.clm.clmwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.security.jwt.JwtGenerator;

@SpringBootTest
class ClmWebsiteApplicationTests {

    @Test
    void contextLoads() {
        JwtGenerator jwt = new JwtGenerator();
        System.out.println(jwt.generateToken("", 1L));
    }

}
