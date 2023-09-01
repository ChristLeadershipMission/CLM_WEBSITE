package worldwide.clm.clmwebsite.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminInvitationRequest {
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
