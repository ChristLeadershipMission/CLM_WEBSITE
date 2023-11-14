package worldwide.clm.clmwebsite.services.campusServices;

import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.request.CampusUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CampusService {
    CampusDetailsResponse createCampus(CampusCreationRequest campusCreationRequest) throws CampusAlreadyExistsException, UserNotFoundException;
    CampusDetailsResponse findCampusById(Long id) throws CampusNotFoundException, UserNotFoundException;
    CampusDetailsResponse findCampusByName(String name) throws CampusNotFoundException, UserNotFoundException;
    List<CampusDetailsResponse> findAllCampuses() throws UserNotFoundException;
    void removeCampus(Long id) throws CampusNotFoundException;
    CampusDetailsResponse updateCampusDetails(Long id, CampusUpdateRequest updatePayLoad) throws UserNotFoundException;

}
