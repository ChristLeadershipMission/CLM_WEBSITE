package worldwide.clm.clmwebsite.services.eventServices;

import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.EventResponse;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.EventNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.List;

public interface EventService {

    ApiResponse createEvent(EventCreationRequest eventCreationRequest) throws UserNotFoundException, CampusNotFoundException;

    EventResponse findById(Long id) throws EventNotFoundException, UserNotFoundException, CampusNotFoundException;

    ApiResponse deleteEventByEventId(Long id);
    ApiResponse updateEventInfo(EventUpdateRequest eventUpdateRequest) throws EventNotFoundException, UserNotFoundException, CampusNotFoundException;

    List<EventResponse> findAll();

    List<EventResponse> findByCampusId(Long campusId);

    Long getCount();
}
