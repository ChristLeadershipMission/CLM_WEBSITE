package worldwide.clm.clmwebsite.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventCreationRequest {

    private String eventName;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long campusId;
    private String eventImageUrl;
    private String eventVideoUrl;

}
