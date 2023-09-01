package worldwide.clm.clmwebsite.services.eventServices.eventUpdate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Event;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.services.eventServices.eventService.EventService;
import worldwide.clm.clmwebsite.utils.GenerateEventApiResponseMessage;

@Service
@AllArgsConstructor
public class EventUpdateServiceImp implements EventUpdateService {

    private final EventService eventService;

    @Override
    public ApiResponse updateEventInfo(String eventName, EventUpdateRequest eventUpdateRequest) {
        System.out.println("i am the eventName " + eventName);

        System.out.println("I'm the first sout of eventUpdateRequest " + eventUpdateRequest.getContent());


        if(findEvent(eventName)==null){ return GenerateEventApiResponseMessage.noEventFound(GenerateEventApiResponseMessage.NO_EVENT_FOUND);}

       Event event =  updateEvent(eventName, eventUpdateRequest);


        System.out.println("I'm the event here " + event.getContent()) ;
        System.out.println("i am the event startDate " + event.getStartDate());

        System.out.println("i reached here");

        return GenerateEventApiResponseMessage.updated(GenerateEventApiResponseMessage.EVENT_UPDATED_SUCCESSFULLY);
    }


    private Event updateEvent(String eventName, EventUpdateRequest eventUpdateRequest) {
        System.out.println("I entered here");
        Event event = findEvent(eventName);
        System.out.println(eventUpdateRequest.getStartDate());
        if(eventUpdateRequest.getEventName()!=null){
            event.setEventName(eventUpdateRequest.getEventName());
        }
        if(eventUpdateRequest.getContent()!=null){
            event.setContent(eventUpdateRequest.getContent());
        }
        if(eventUpdateRequest.getStartDate()!=null){
            event.setStartDate(eventUpdateRequest.getStartDate());
        }
        if(eventUpdateRequest.getEndDate()!=null){
            event.setEndDate(eventUpdateRequest.getEndDate());
        }
        if(eventUpdateRequest.getEventImageUrl()!=null){
            event.setEventImageUrl(eventUpdateRequest.getEventImageUrl());
        }
        if(eventUpdateRequest.getEventVideoUrl()!=null){
            event.setEventVideoUrl(eventUpdateRequest.getEventVideoUrl());
        }

        Event savedEvent  =  eventService.save(event);
        return savedEvent;
    }
        private Event findEvent (String eventName){
            return eventService.findByEventName(eventName).orElse(null);
        }

}
