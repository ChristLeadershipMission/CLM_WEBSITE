package worldwide.clm.clmwebsite.services.eventServices.eventService;

import worldwide.clm.clmwebsite.data.models.Event;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;

import java.util.Optional;

public interface EventService {

    Event save(Event event);


    Optional<Event> findByEventName(String eventName);

    ApiResponse deleteEventByEventName(String eventName);

}
