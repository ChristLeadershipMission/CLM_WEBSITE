package worldwide.clm.clmwebsite.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class AdminResponse {
    private Long id;
    private BioDataResponse bioData;
}
