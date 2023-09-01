package worldwide.clm.clmwebsite.controllers.eventController;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.services.eventServices.eventCreation.EventCreationService;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")

public class EventCreationController {

    private final EventCreationService eventCreationService;

    @PostMapping("eventCreation")
    public ResponseEntity<ApiResponse> createEvent( @RequestBody EventCreationRequest eventCreationRequest){
        return new ResponseEntity<>(eventCreationService.createEvent(eventCreationRequest), HttpStatus.OK);
    }
}
