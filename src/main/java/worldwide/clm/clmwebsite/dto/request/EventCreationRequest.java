package worldwide.clm.clmwebsite.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "campusId must not be null")
    private Long campusId;
    private String eventImageUrl;
    private String eventVideoUrl;

}
