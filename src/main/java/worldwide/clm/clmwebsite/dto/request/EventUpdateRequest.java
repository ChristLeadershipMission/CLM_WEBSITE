package worldwide.clm.clmwebsite.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventUpdateRequest {
    private Long id;
    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String eventImageUrl;
    private String eventVideoUrl;
    @NotNull(message = "campusId must not be null")
    private Long campusId;
}
