package worldwide.clm.clmwebsite.dto.response;

import lombok.*;
import worldwide.clm.clmwebsite.data.models.Address;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.data.models.Department;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MemberResponse {
    private Long id;
    private Address address;
    private BioDataResponse bioData;
    private LocalDateTime dateOfBirth;
    private Department department;
    private String levelOfEducation;
    private String occupation;
    private String academicHonour;
    private String maritalStatus;
    private Campus campus;
}
