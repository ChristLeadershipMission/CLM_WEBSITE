package worldwide.clm.clmwebsite.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import worldwide.clm.clmwebsite.data.repositories.MinisterRepository;

import java.time.LocalDateTime;

@Setter
@Getter
public class MinisterRegistrationRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String profilePicture;
    private LocalDateTime createdAt;
    private String portfolio;
}
