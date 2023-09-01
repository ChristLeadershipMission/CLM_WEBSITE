package worldwide.clm.clmwebsite.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.EventResponse;
import worldwide.clm.clmwebsite.exception.EventNotFoundException;
import worldwide.clm.clmwebsite.services.eventServices.EventService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clmWebsite/api/v1/event/")
public class EventController {

    private final EventService eventService;

    @PostMapping("eventCreation")
    public ResponseEntity<ApiResponse> createEvent(@RequestBody EventCreationRequest eventCreationRequest){
        return new ResponseEntity<>(eventService.createEvent(eventCreationRequest), HttpStatus.OK);
    }
    @GetMapping("findById/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.findById(id), HttpStatus.OK);
    }
    @GetMapping("findAll")
    public ResponseEntity<List<EventResponse>> findAll() {
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("deleteEventById")
    public ResponseEntity<ApiResponse> deleteEventByEventName(@RequestParam Long id){
        return new ResponseEntity<>(eventService.deleteEventByEventId(id), HttpStatus.OK);
    }
    @PatchMapping("eventUpdate")
    public ResponseEntity<ApiResponse> updateEvent(@RequestBody EventUpdateRequest eventUpdateRequest) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.updateEventInfo(eventUpdateRequest), HttpStatus.OK);
    }
}
