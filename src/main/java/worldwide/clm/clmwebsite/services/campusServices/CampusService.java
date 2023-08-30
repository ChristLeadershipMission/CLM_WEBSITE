package worldwide.clm.clmwebsite.services.campusServices;

import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.BusinessLogicException;
import java.util.List;
import java.util.Optional;
import com.github.fge.jsonpatch.JsonPatch;

public interface CampusService {
    void createCampus(CampusCreationRequest campusCreationRequest) throws BusinessLogicException;
    Optional<Campus> findCampusById(Long id) throws BusinessLogicException;
    Optional<Campus> findCampusByName(String name) throws BusinessLogicException;
    List<Campus> findAllCampuses() throws BusinessLogicException;
    void removeCampus(Long id) throws BusinessLogicException;
    CampusDetailsResponse updateCampusDetails(Long id, JsonPatch updatePayLoad);

}
