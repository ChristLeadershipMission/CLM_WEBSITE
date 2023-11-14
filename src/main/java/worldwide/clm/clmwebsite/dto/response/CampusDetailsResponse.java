package worldwide.clm.clmwebsite.dto.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import worldwide.clm.clmwebsite.data.models.Address;
import worldwide.clm.clmwebsite.data.models.Minister;

@Setter
@Getter
@Builder
public class CampusDetailsResponse {
    private Long id;
    private String name;
    private String email;
    private Minister ministerInCharge;
    private Address address;
    private String logo;
}
