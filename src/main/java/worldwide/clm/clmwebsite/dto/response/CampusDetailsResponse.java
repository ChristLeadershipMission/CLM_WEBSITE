package worldwide.clm.clmwebsite.dto.response;

import lombok.*;
import worldwide.clm.clmwebsite.data.models.Address;
import worldwide.clm.clmwebsite.data.models.Minister;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class CampusDetailsResponse {
    private String name;
    private Minister ministerInCharge;
    private Address address;
}
