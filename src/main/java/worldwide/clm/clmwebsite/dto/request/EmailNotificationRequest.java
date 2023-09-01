package worldwide.clm.clmwebsite.dto.request;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailNotificationRequest {
	private List<Recipient> to = new ArrayList<> ();
	private String subject;
	private String text;
	private String htmlContent;
}
