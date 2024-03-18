package worldwide.clm.clmwebsite.dto.request;

import lombok.*;
import worldwide.clm.clmwebsite.data.models.Address;
import worldwide.clm.clmwebsite.data.models.Minister;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CampusCreationRequest {
    private String name;
    private Long ministerInChargeId;
    private Address address;
    private String email;
    private String logo;
    private String buttonColour;
}
