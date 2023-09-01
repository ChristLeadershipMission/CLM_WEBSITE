package worldwide.clm.clmwebsite.services.campusServices;

import com.github.fge.jsonpatch.JsonPatch;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CampusService {
    void createCampus(CampusCreationRequest campusCreationRequest) throws CampusAlreadyExistsException;
    Optional<Campus> findCampusById(Long id) ;
    Optional<Campus> findCampusByName(String name) ;
    List<Campus> findAllCampuses() ;
    void removeCampus(Long id) throws CampusNotFoundException;
    CampusDetailsResponse updateCampusDetails(Long id, JsonPatch updatePayLoad);

}
