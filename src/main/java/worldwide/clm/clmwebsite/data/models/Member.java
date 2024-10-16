package worldwide.clm.clmwebsite.data.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import worldwide.clm.clmwebsite.enums.Role;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Address address;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BioData bioData;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateOfBirth;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Department department;
    private String levelOfEducation;
    private String occupation;
    private String academicHonour;
    private String maritalStatus;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Campus campus;
}
