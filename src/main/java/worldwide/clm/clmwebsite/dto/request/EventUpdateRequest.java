package worldwide.clm.clmwebsite.dto.request;


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

    private String eventName;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private String eventImageUrl;
    private String eventVideoUrl;
}
