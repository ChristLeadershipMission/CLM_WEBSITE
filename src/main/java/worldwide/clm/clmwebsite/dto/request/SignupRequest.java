package worldwide.clm.clmwebsite.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
	@NotNull(message = "Field firstName is required")
	private String firstName;
	@NotNull(message = "Field lastName is required")
	private String lastName;
	@NotNull(message = "Field email is required")
	@Email
	private String email;
	@NotNull(message = "Field password is required")
	private String password;
}
