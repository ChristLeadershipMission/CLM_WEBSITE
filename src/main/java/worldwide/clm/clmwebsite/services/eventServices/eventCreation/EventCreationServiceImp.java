package worldwide.clm.clmwebsite.services.eventServices.eventCreation;


import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Event;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.services.eventServices.eventService.EventService;
import worldwide.clm.clmwebsite.utils.GenerateEventApiResponseMessage;

@Service
@AllArgsConstructor
public class EventCreationServiceImp implements EventCreationService {

    private final ModelMapper modelMapper;
    private final EventService eventService;
    @Override
    public ApiResponse createEvent(EventCreationRequest eventCreationRequest) {
        if(isAlreadyCreated(eventCreationRequest.getEventName())) return GenerateEventApiResponseMessage.alreadyCreated(GenerateEventApiResponseMessage.EVENT_ALREADY_CREATED);
        Event event =modelMapper.map(eventCreationRequest, Event.class);
        eventService.save(event);
        return GenerateEventApiResponseMessage.created(GenerateEventApiResponseMessage.EVENT_CREATED_SUCCESSFULLY);
    }

    private boolean isAlreadyCreated(String eventName) {
        return eventService.findByEventName(eventName).isPresent();
    }
}
