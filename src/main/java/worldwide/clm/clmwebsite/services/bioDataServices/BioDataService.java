package worldwide.clm.clmwebsite.services.bioDataServices;

import worldwide.clm.clmwebsite.dto.request.ResetPasswordRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.BioDataResponse;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

public interface BioDataService {
    BioDataResponse findByEmail(String email) throws UserNotFoundException;

    void validateDuplicateUserExistence(String emailAddress) throws UserAlreadyExistsException;

    ApiResponse resetPassword(String email, String newPassword) throws UserNotFoundException;

    boolean passwordMatch(String emailAddress, String oldPassword);
}
