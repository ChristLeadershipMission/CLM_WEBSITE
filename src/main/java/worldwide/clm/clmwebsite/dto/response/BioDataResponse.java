package worldwide.clm.clmwebsite.dto.response;

import lombok.*;
import worldwide.clm.clmwebsite.enums.Role;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BioDataResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddress;
    private List<Role> roles;
    private Boolean isEnabled;
}
