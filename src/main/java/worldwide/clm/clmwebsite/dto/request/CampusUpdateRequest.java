package worldwide.clm.clmwebsite.dto.request;

import lombok.Getter;
import lombok.Setter;
import worldwide.clm.clmwebsite.data.models.Address;

@Setter
@Getter
public class CampusUpdateRequest {
    private String name;
    private Long ministerInChargeId;
    private String email;
    private String logo;
}
