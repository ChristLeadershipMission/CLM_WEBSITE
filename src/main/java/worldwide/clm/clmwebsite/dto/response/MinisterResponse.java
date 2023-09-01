package worldwide.clm.clmwebsite.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MinisterResponse {
    private Long id;
    private BioDataResponse bioData;
    private String portfolio;
}
