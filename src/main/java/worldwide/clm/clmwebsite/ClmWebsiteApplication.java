package worldwide.clm.clmwebsite;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@OpenAPIDefinition(
//        info = @Info(
//                title = "CLM campus management",
//                version = "v1",
//                description = "This app provides campus management",
//                contact = @Contact(
//                        name = "Samuel",
//                        email = "samuelabiodun324@gmail.com"
//                )
//        )
//)
public class ClmWebsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClmWebsiteApplication.class, args);
    }

}
