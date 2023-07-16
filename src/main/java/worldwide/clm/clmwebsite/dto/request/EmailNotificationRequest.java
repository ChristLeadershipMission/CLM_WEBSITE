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
	private final Sender sender = new Sender("CLM", "noreply@clm.co.uk");
	private List<Recipient> to = new ArrayList<> ();
	private final String subject = "Christian Leadership Ministry!";
	private String htmlContent;
}
