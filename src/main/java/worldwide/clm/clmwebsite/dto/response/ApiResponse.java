package worldwide.clm.clmwebsite.dto.response;

import lombok.*;
import worldwide.clm.clmwebsite.common.Message;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
	private Message message;
	private int statusCode;
	private boolean success;
}
