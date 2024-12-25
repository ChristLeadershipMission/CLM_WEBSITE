package worldwide.clm.clmwebsite.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
	private String message;
	private int statusCode;
	private boolean success;
	private int size;
	private Object data;
}
