package worldwide.clm.clmwebsite.dto.response;

import jakarta.persistence.*;
import lombok.*;
import worldwide.clm.clmwebsite.data.models.Address;
import worldwide.clm.clmwebsite.data.models.Minister;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampusDetailsResponse {
    private Long id;
    private String name;
    private String email;
    private Minister ministerInCharge;
    private Address address;
    private String logo;
    private String buttonColour;
}
