package worldwide.clm.clmwebsite.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.enums.EventMode;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventResponse {
    private Long id;
    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampusDetailsResponse campus;
    private String flier;
    private String eventImageUrl;
    private String eventVideoUrl;
    private EventMode mode;
}
