package worldwide.clm.clmwebsite.services.eventServices.eventCreation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;



 @SpringBootTest
class EventCreationServiceImpTest {

     @Autowired
     private EventCreationService eventCreationService;

     @Test
     public void testThatAnEventCanBeCreated(){

         EventCreationRequest eventCreationRequest = new EventCreationRequest();
         eventCreationRequest.setEventName("5 Nights of Glory");
         eventCreationRequest.setStartDate(LocalDate.parse("2023-10-10"));
         eventCreationRequest.setEndDate(LocalDate.of(2023, 10, 10).plusDays(5));

         var response = eventCreationService.createEvent(eventCreationRequest);


         EventCreationRequest eventCreationRequest1 = new EventCreationRequest();
         eventCreationRequest1.setEventName("5 Nights of Glory");
         eventCreationRequest1.setStartDate(LocalDate.parse("2023-10-10"));
         eventCreationRequest1.setEndDate(LocalDate.of(2023, 10, 12));
         var response1 = eventCreationService.createEvent(eventCreationRequest);

         System.out.println("I'm the first event's response : " + response.getMessage());
         System.out.println("I'm the second event's response : " + response1.getMessage());
         assertThat(response.isSuccess()).isTrue();

         assertThat(response1.isSuccess()).isFalse();

     }
}