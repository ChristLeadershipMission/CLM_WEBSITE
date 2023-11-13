package worldwide.clm.clmwebsite.services.ministerServices;

import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

public interface MinisterService {
    Minister findByEmail(String emailAddress) throws UserNotFoundException;
    Minister findById(Long id) throws UserNotFoundException;
}
