package worldwide.clm.clmwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import worldwide.clm.clmwebsite.data.models.Admin;
import worldwide.clm.clmwebsite.data.models.BioData;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.data.repositories.AdminRepository;
import worldwide.clm.clmwebsite.data.repositories.CampusRepository;
import worldwide.clm.clmwebsite.data.repositories.MinisterRepository;
import worldwide.clm.clmwebsite.enums.Role;
import worldwide.clm.clmwebsite.utils.JwtUtility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
class ClmWebsiteApplicationTests {
    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    private MinisterRepository ministerRepository;
    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void
    contextLoads() {
        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
    }

    @Test
    void admin(){
        Admin admin = new Admin();
        BioData bioData = new BioData();
        bioData.setPassword(passwordEncoder.encode(
                "moyinoluwa"
        ));
        bioData.setRoles(List.of(Role.SUPER_ADMIN));
        bioData.setFirstName("Moyinoluwa");
        bioData.setLastName("Michael");
        bioData.setEmailAddress("ogunsmoyin.m@gmail.com");
        bioData.setPhoneNumber("08089649909");
        admin.setBioData(bioData);
        adminRepository.save(admin);
    }
    @Test
    void minister(){
        Minister minister = new Minister();
        minister.setEmailAddress("lekan@gmail.com");
        minister.setFirstName("lekan");
        minister.setLastName("Olami");
        minister.setPortfolio("ICT DIRECTOR");
        minister.setId(1L);
        ministerRepository.save(minister);
    }
    @Test
    void campus(){
        Campus campus = new Campus();
        for (var eachCampus:campusRepository.findAll()){
            if (eachCampus.getMinisterInChargeId() == 1L){
                eachCampus.setMinisterInChargeId(202L);
                campusRepository.save(eachCampus);
            }
        }
    }

}
