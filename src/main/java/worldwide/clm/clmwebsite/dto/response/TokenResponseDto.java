package worldwide.clm.clmwebsite.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {
	private String accessToken;
	private String refreshToken;
}
