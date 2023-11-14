package worldwide.clm.clmwebsite.services.ministerServices;

import com.github.fge.jsonpatch.JsonPatch;
import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.dto.request.MinisterRegistrationRequest;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.List;

public interface MinisterService {
    Minister findByEmail(String emailAddress) throws UserNotFoundException;
    Minister findById(Long id) throws UserNotFoundException;
    List<Minister> findAll() throws UserNotFoundException;

    Minister createMinister(MinisterRegistrationRequest ministerRegistrationRequest);

    Minister updateMinisterDetails(Long id, MinisterRegistrationRequest ministerUpdateRequest);

    void deleteMinister(Long id);

    Long getCount();
}
