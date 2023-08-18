package worldwide.clm.clmwebsite;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Furniture API",
                version = "v1",
                description = "This app provides REST APIs for Furniture",
                contact = @Contact(
                        name = "Furniture Support",
                        email = "jojololamartins686@gmail.com"
                )
        )
)
public class ClmWebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClmWebsiteApplication.class, args);
    }

}
