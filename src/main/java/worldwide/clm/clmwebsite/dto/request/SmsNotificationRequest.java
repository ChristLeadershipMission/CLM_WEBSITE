package worldwide.clm.clmwebsite.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SmsNotificationRequest {
    private List<String> recipientPhoneNumber;
    private String message;
}
