package worldwide.clm.clmwebsite.services.bioDataServices;

import worldwide.clm.clmwebsite.dto.response.BioDataResponse;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

public interface BioDataService {
    BioDataResponse findByEmail(String email) throws UserNotFoundException;

    void validateDuplicateUserExistence(String emailAddress) throws UserAlreadyExistsException;
}
