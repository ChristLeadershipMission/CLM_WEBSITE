package worldwide.clm.clmwebsite.controllers.eventController;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.services.eventServices.eventUpdate.EventUpdateService;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/")
@CrossOrigin(origins = "*")
public class EventUpdateController {
    private final EventUpdateService eventUpdateService;

    @PostMapping("eventUpdate")
    public ResponseEntity<ApiResponse> updateEvent(@RequestParam String eventName, @RequestBody EventUpdateRequest eventUpdateRequest){
        return new ResponseEntity<>(eventUpdateService.updateEventInfo(eventName, eventUpdateRequest), HttpStatus.OK);
    }
}
