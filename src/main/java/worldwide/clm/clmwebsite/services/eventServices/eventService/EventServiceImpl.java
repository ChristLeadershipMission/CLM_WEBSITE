package worldwide.clm.clmwebsite.services.eventServices.eventService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Event;
import worldwide.clm.clmwebsite.data.repositories.EventRepository;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.utils.GenerateEventApiResponseMessage;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> findByEventName(String eventName) {
        return eventRepository.findByEventName(eventName);
    }

    @Override
    public ApiResponse deleteEventByEventName(String eventName) {
        var event = findByEventName(eventName);
        if (event.isEmpty()) return GenerateEventApiResponseMessage.noEventFound(GenerateEventApiResponseMessage.NO_EVENT_FOUND);
        eventRepository.delete(event.get());
        return GenerateEventApiResponseMessage.eventDeleted(GenerateEventApiResponseMessage.EVENT_DELETED_SUCCESSFULLY);
    }
}
