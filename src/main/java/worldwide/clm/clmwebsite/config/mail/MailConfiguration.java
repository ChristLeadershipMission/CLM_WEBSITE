package worldwide.clm.clmwebsite.config.mail;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MailConfiguration {
	private String apikey;
	private String mailUrl;
}
