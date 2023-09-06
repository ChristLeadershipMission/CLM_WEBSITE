package worldwide.clm.clmwebsite.services.ministerServices;

import worldwide.clm.clmwebsite.dto.response.MinisterResponse;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

public interface MinisterService {
    MinisterResponse findByEmail(String emailAddress) throws UserNotFoundException;
    MinisterResponse findById(Long id) throws UserNotFoundException;
}
