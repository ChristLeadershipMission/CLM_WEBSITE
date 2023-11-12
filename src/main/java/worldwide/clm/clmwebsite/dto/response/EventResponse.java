package worldwide.clm.clmwebsite.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventResponse {
    private Long id;
    private String eventName;
    private String theme;
    private LocalDate startDate;
    private LocalDate endDate;
    private String flier;
    private String eventImageUrl;
    private String eventVideoUrl;
}
