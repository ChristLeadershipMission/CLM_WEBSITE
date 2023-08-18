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
	private final String sender = "ogunsmoyin.m@gmail.com";
	private List<Recipient> to = new ArrayList<> ();
	private String subject;
	private String htmlContent;
}
