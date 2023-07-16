package worldwide.clm.clmwebsite.exception;

public class UserAlreadyExistsException extends BusinessLogicException {
	public UserAlreadyExistsException(String message) {
		super (message);
	}
}
