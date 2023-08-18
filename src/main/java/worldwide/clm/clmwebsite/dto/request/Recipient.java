package worldwide.clm.clmwebsite.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Recipient {
	private String firstName;
	private String email;
}
