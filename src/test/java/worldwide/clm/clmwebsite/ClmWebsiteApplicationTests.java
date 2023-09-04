package worldwide.clm.clmwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.utils.JwtUtility;

@SpringBootTest
class ClmWebsiteApplicationTests {
    @Autowired
    JwtUtility jwtUtility;

    @Test
    void
    contextLoads() {
        Campus.builder()
                .name("TASUED")
                .build();
    }

}
