package worldwide.clm.clmwebsite;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import worldwide.clm.clmwebsite.data.models.*;
import worldwide.clm.clmwebsite.data.repositories.*;
import worldwide.clm.clmwebsite.enums.Channel;
import worldwide.clm.clmwebsite.enums.MediaCategory;
import worldwide.clm.clmwebsite.enums.Role;
import worldwide.clm.clmwebsite.utils.JwtUtility;

import java.util.List;

@SpringBootTest
class ClmWebsiteApplicationTests {
    @Autowired
    JwtUtility jwtUtility;

    @Autowired
    private MinisterRepository ministerRepository;
    @Autowired
    private AudioRepository audioRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    @Test
    void
    contextLoads() {
        System.out.println(jwtUtility.generateEncryptedLink("Rova Bank"));
//        System.out.println(LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
    }

    @Test
    void admin() {
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
    void MediaMgt(){
        videoRepository.save(
          Video.builder()
                  .title("TIME NOT SPENT PRAYING IS TIME WASTED")
                  .category(MediaCategory.PRAYER)
                  .videoUrl("https://www.youtube.com/watch?v=_owh9oTCiG8")
                  .channel(Channel.YOUTUBE)
                  .build()
        );
    }
    @Test
    void minister() {
        for (var each : ministerRepository.findAll()) {
            System.out.println(each);
        }
    }

    @Test
    void saveDefaultMinister() {
        Minister minister = new Minister();
        minister.setEmailAddress("taiwoadewal@gmail.com");
        minister.setFirstName("Taiwo");
        minister.setLastName("Adewale");
        minister.setPortfolio("President, CLM Worldwide");
        minister.setId(100L);
        String password = passwordEncoder.encode("ClmWorldwide");
        minister.setPassword(password);
        ministerRepository.save(minister);
    }

    @Test
    void campus() {
        Campus campus = new Campus();
        for (var eachCampus : campusRepository.findAll()) {
            if (eachCampus.getMinisterInChargeId() == 1L) {
                eachCampus.setMinisterInChargeId(202L);
                campusRepository.save(eachCampus);
            }
        }
    }

    @Test
    void saveDeffaultCampus() {
        Address address = new Address();
        address.setCity("Wasinmi");
        address.setState("Ogun");
        address.setCountry("Nigeria");
        address.setStreetName("Tabernacle of Mercy, Ogbere Road");
        Campus campus = new Campus();
        campus.setId(10L);
        campus.setName("OWUTECH");
        campus.setEmail("taiwoadewal@gmail.com");
        campus.setMinisterInChargeId(100L);
        campus.setAddress(address);
        campusRepository.save(campus);
    }

}
