package worldwide.clm.clmwebsite.services.eventServices;

import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.EventResponse;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.EventNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;

import java.util.List;

public interface EventService {

    ApiResponse createEvent(EventCreationRequest eventCreationRequest, MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException;

    EventResponse findById(Long id, MinisterService ministerService) throws EventNotFoundException, UserNotFoundException, CampusNotFoundException;

    ApiResponse deleteEventByEventId(Long id);
    ApiResponse updateEventInfo(EventUpdateRequest eventUpdateRequest, MinisterService ministerService) throws EventNotFoundException, UserNotFoundException, CampusNotFoundException;

    List<EventResponse> findAll(MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException;

    List<EventResponse> findByCampusId(Long campusId, MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException;

    Long getCount();

    List<EventResponse> searchByName(String name, MinisterService ministerService) throws UserNotFoundException, CampusNotFoundException;

    void resetToDefaultCampusEventsWithId(Long campusId);
}
