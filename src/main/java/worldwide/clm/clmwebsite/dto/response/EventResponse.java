package worldwide.clm.clmwebsite.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventResponse {
    private String eventName;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private String eventImageUrl;
    private String eventVideoUrl;
}
