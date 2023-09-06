package worldwide.clm.clmwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.data.models.BioData;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.data.repositories.CampusRepository;
import worldwide.clm.clmwebsite.data.repositories.MinisterRepository;
import worldwide.clm.clmwebsite.utils.JwtUtility;

@SpringBootTest
class ClmWebsiteApplicationTests {
    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    private MinisterRepository ministerRepository;
    @Autowired
    private CampusRepository campusRepository;

    @Test
    void
    contextLoads() {
        ministerRepository.save(
                Minister.builder()
                        .bioData(
                                BioData.builder()
                                        .firstName("Mike")
                                        .lastName("Adebowale")
                                        .build()
                        )
                        .build()
        );
    }

    @Test
    void campusTest(){
        campusRepository.save(
                Campus.builder()
                        .name("TASUED")
                        .ministerInCharge(
                                Minister.builder()
                                        .id(1L)
                                        .build()
                        )
                        .build()
        );
    }

}
