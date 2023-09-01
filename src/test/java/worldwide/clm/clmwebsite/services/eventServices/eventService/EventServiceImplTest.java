package worldwide.clm.clmwebsite.services.eventServices.eventService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.services.eventServices.eventCreation.EventCreationService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventServiceImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventCreationService eventCreationService;
    @Test
    public void testThatAnEventCanBeDeletedByEventName() {

        EventCreationRequest eventCreationRequest = new EventCreationRequest();
        eventCreationRequest.setEventName("5 Nights of Glory");
        eventCreationRequest.setContent("All lost glory will be restored in Jesus' Name. Come One, Come all");
        eventCreationRequest.setStartDate(LocalDate.of(2023, 11,11));
        eventCreationRequest.setEndDate(eventCreationRequest.getStartDate().plusDays(4));

       ApiResponse createResponse =  eventCreationService.createEvent(eventCreationRequest);
        assertThat(createResponse.isSuccess()).isTrue();

        ApiResponse deleteResponse = eventService.deleteEventByEventName(eventCreationRequest.getEventName());
        assertThat(deleteResponse.isSuccess()).isTrue();
        assertThat(eventService.findByEventName(eventCreationRequest.getEventName())).isEmpty();
    }
}