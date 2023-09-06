package worldwide.clm.clmwebsite.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Address;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "description is required")
    private String description;
    private String groupPicture;
    private Address address;
    private Long ministerInCharge;
}
