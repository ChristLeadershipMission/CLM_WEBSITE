package worldwide.clm.clmwebsite.services.eventServices;

import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.EventResponse;
import worldwide.clm.clmwebsite.exception.EventNotFoundException;

import java.util.List;

public interface EventService {

    ApiResponse createEvent(EventCreationRequest eventCreationRequest);

    EventResponse findById(Long id) throws EventNotFoundException;

    ApiResponse deleteEventByEventId(Long id);
    ApiResponse updateEventInfo(EventUpdateRequest eventUpdateRequest) throws EventNotFoundException;

    List<EventResponse> findAll();
}
