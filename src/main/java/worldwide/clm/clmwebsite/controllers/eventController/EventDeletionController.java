package worldwide.clm.clmwebsite.controllers.eventController;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.services.eventServices.eventService.EventService;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class EventDeletionController {

    private final EventService eventService;

    @PostMapping("eventDeletionByName")
    public ResponseEntity<ApiResponse> deleteEventByEventName(@RequestParam String eventName){
        return new ResponseEntity<>(eventService.deleteEventByEventName(eventName), HttpStatus.OK);
    }
}
