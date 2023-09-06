package worldwide.clm.clmwebsite.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangePasswordRequest {
    private String emailAddress;
    private String oldPassword;
    private String newPassword;
}
