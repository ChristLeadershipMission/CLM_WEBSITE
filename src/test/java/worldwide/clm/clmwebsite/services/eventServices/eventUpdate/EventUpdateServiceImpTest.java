package worldwide.clm.clmwebsite.services.eventServices.eventUpdate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.data.models.Event;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.services.eventServices.eventCreation.EventCreationService;
import worldwide.clm.clmwebsite.services.eventServices.eventService.EventService;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventUpdateServiceImpTest {

    @Autowired
    private EventUpdateService eventUpdateService;
    @Autowired
    private EventCreationService eventCreationService;
     @Autowired
     private EventService eventService;

    @Test
    public void testThatAnEventCanBeUpdated(){

        EventCreationRequest eventCreationRequest = new EventCreationRequest();
        eventCreationRequest.setEventName("5 Nights of Glory");
        eventCreationRequest.setStartDate(LocalDate.parse("2023-10-10"));
        eventCreationRequest.setEndDate(LocalDate.of(2023, 10, 10).plusDays(5));
        var response = eventCreationService.createEvent(eventCreationRequest);


            EventUpdateRequest eventUpdateRequest = new EventUpdateRequest();
            eventUpdateRequest.setEndDate(LocalDate.of(2023, 11, 12));
            eventUpdateRequest.setContent("This is a power packed program where God will be ministering to everyone. Don't miss it for anything");
        var  response1 = eventUpdateService.updateEventInfo(eventCreationRequest.getEventName(), eventUpdateRequest);

        assertThat(response.isSuccess()).isTrue();
        assertThat(response1.isSuccess()).isTrue();
    }

}