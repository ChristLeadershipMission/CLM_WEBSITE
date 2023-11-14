package worldwide.clm.clmwebsite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.EventResponse;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.EventNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.eventServices.EventService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/clmWebsite/api/v1/event/")
public class EventController {

    private final EventService eventService;
    @Operation(
            summary = "Create Event",
            description = "API for creating a new event."
    )
    @Parameter(
            name = "eventCreationRequest",
            description = "Request containing event details for creation.",
            required = true,
            in = ParameterIn.PATH,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventCreationRequest.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Event created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("eventCreation")
    public ResponseEntity<ApiResponse> createEvent(@RequestBody EventCreationRequest eventCreationRequest){
        return new ResponseEntity<>(eventService.createEvent(eventCreationRequest), HttpStatus.OK);
    }

    @Operation(
            summary = "Find Event by ID",
            description = "API for retrieving event details by its unique identifier."
    )
    @Parameter(
            name = "id",
            description = "The unique identifier of the event to retrieve.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "integer", format = "int64")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Event found and returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
    )
    @GetMapping("findById/{id}")
    public ResponseEntity<EventResponse> findById(@PathVariable Long id) throws EventNotFoundException, UserNotFoundException, CampusNotFoundException {
        return new ResponseEntity<>(eventService.findById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Find Event by Campus ID",
            description = "API for retrieving event details by Campus ID."
    )
    @Parameter(
            name = "id",
            description = "The unique identifier of the campus whose events are to be retrieved.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(type = "integer", format = "int64")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Events found and returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
    )
    @GetMapping("findByCampusId/{campusId}")
    public ResponseEntity<List<EventResponse>> findByCampusId(@PathVariable Long campusId) throws EventNotFoundException {
        return new ResponseEntity<>(eventService.findByCampusId(campusId), HttpStatus.OK);
    }

    @Operation(
            summary = "Find All Events",
            description = "API for retrieving a list of all events."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "List of events retrieved successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventResponse.class))
    )
    @GetMapping("findAll")
    public ResponseEntity<List<EventResponse>> findAll() {
        return new ResponseEntity<>(eventService.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Event by ID",
            description = "API for deleting an event by its unique identifier."
    )
    @Parameter(
            name = "id",
            description = "The unique identifier of the event to delete.",
            required = true,
            in = ParameterIn.QUERY,
            schema = @Schema(type = "integer", format = "int64")
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Event deleted successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @DeleteMapping("deleteEventById")
    public ResponseEntity<ApiResponse> deleteEventByEventName(@RequestParam Long id){
        return new ResponseEntity<>(eventService.deleteEventByEventId(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Update Event Information",
            description = "API for updating event information."
    )
    @Parameter(
            name = "eventUpdateRequest",
            description = "Request containing updated event information.",
            required = true,
            in = ParameterIn.PATH,
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EventUpdateRequest.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Event information updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @PatchMapping("eventUpdate")
    public ResponseEntity<ApiResponse> updateEvent(@RequestBody EventUpdateRequest eventUpdateRequest) throws EventNotFoundException, UserNotFoundException, CampusNotFoundException {
        return new ResponseEntity<>(eventService.updateEventInfo(eventUpdateRequest), HttpStatus.OK);
    }
}
