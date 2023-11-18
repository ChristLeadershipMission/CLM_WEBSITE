package worldwide.clm.clmwebsite.services.campusServices;

import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.request.CampusUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.eventServices.EventService;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;

import java.util.List;

public interface CampusService {
    CampusDetailsResponse createCampus(CampusCreationRequest campusCreationRequest, MinisterService ministerService) throws CampusAlreadyExistsException, UserNotFoundException;
    CampusDetailsResponse findCampusById(Long id, MinisterService ministerService) throws CampusNotFoundException, UserNotFoundException;
    CampusDetailsResponse findCampusByName(String name, MinisterService ministerService) throws CampusNotFoundException, UserNotFoundException;
    List<CampusDetailsResponse> findAllCampuses(MinisterService ministerService) throws UserNotFoundException;
    void removeCampus(Long id, EventService eventService) throws CampusNotFoundException;
    CampusDetailsResponse updateCampusDetails(Long id, CampusUpdateRequest updatePayLoad, MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException;

    Long getCount();

    List<CampusDetailsResponse> searchByName(String name, MinisterService ministerService) throws UserNotFoundException;

    void resetToDefaultMinisterCampusesWithId(Long ministerId);
}
