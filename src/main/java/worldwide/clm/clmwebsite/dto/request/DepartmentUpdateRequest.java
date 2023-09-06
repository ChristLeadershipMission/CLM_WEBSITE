package worldwide.clm.clmwebsite.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentUpdateRequest {
    private String name;
    private String description;
    private Long ministerInCharge;
}
