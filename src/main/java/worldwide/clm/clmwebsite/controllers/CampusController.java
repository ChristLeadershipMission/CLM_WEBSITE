package worldwide.clm.clmwebsite.controllers;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.request.CampusUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.campusServices.CampusService;
import worldwide.clm.clmwebsite.services.eventServices.EventService;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clmWebsite/api/v1/campus/")
@RequiredArgsConstructor
public class CampusController {

    private final CampusService campusService;
    private final MinisterService ministerService;
    private final EventService eventService;

    @Operation(
            summary = "Create Campus",
            description = "An API handling campus creation"
    )
    @Parameter(
            name = "CampusCreationRequest",
            description = "Containing all the necessary fields required to make a campus exist",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = CampusCreationRequest.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Creation successful",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("createCampus")
    public ResponseEntity<CampusDetailsResponse> createCampus(@RequestBody CampusCreationRequest campusCreationRequest) throws CampusAlreadyExistsException, UserNotFoundException {
        CampusDetailsResponse campusDetailsResponse = campusService.createCampus(campusCreationRequest, ministerService);
        return ResponseEntity.status(HttpStatus.CREATED).body(campusDetailsResponse);
    }

    @Operation(
            summary = "Update Campus Details",
            description = "An API for updating campus details using JSON Patch."
    )

    @Parameter(
            name = "campusUpdateRequest",
            description = "JSON Patch document containing updates for the campus.",
            required = true,
            in = ParameterIn.PATH,
            content = @Content(mediaType = "application/json-patch+json",
                    schema = @Schema(implementation = JsonPatch.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Campus details updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Campus.class))
    )
    @PatchMapping(value = "updateCampus/{id}")
    public ResponseEntity<?> updateCampusDetails
            (@PathVariable Long id, @RequestBody CampusUpdateRequest campusUpdateRequest) throws UserNotFoundException, CampusNotFoundException {
        CampusDetailsResponse updatedCampus = campusService.updateCampusDetails(id, campusUpdateRequest, ministerService);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCampus);
    }

    @Operation(
            summary = "Get Campus by ID",
            description = "Retrieve campus details by its unique identifier."
    )
    @Parameter(
            name = "id",
            description = "The unique identifier of the campus to be retrieved.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = Long.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Campus found and returned",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campus not found")
    @GetMapping("getCampusById/{id}")
    public ResponseEntity<?> getCampusById(@PathVariable Long id) throws UserNotFoundException, CampusNotFoundException {
        CampusDetailsResponse campus = campusService.findCampusById(id, ministerService);
        return new ResponseEntity<>(campus, HttpStatus.OK);
    }

    @Operation(
            summary = "Search Campus by name",
            description = "API for retrieving campus details by its name."
    )
    @Parameter(
            name = "name",
            description = "The letter(s) containing in the name of the campus to be retrieved.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = String.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Campus found and returned",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campus not found")
    @GetMapping("searchByName/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name) throws UserNotFoundException, CampusNotFoundException {
        List<CampusDetailsResponse> campuses = campusService.searchByName(name, ministerService);
        return new ResponseEntity<>(campuses, HttpStatus.OK);
    }

    @Operation(
            summary = "Get Campus by name",
            description = "Retrieve campus details by its name"
    )
    @Parameter(
            name = "name",
            description = "The name of the campus to be retrieved",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = String.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "302",
            description = "Campus found and returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campus not found")
    @GetMapping("getCampusByName/{name}")
    public ResponseEntity<?> getCampusByName(@PathVariable String name) throws UserNotFoundException, CampusNotFoundException {
        CampusDetailsResponse campus = campusService.findCampusByName(name, ministerService);
        return new ResponseEntity<>(campus, HttpStatus.FOUND);
    }

    @Operation(
            summary = "Get all campuses",
            description = "Retrieve all campuses"
    )
    @Parameter(
            name = "",
            description = "",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = Object.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Campuses found and returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campuses not found")
    @GetMapping("findAllCampuses")
    public ResponseEntity<?> getAllCampuses() throws UserNotFoundException {
        List<CampusDetailsResponse> campuses = campusService.findAllCampuses(ministerService);
        return new ResponseEntity<>(campuses, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete Campus by id",
            description = "Delete campus details by its unique identifier"
    )
    @Parameter(
            name = "id",
            description = "The unique identifier of the campus to be retrieved",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = Long.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Removal successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campus not found")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> removeCampus(@PathVariable Long id) throws CampusNotFoundException {
        campusService.removeCampus(id, eventService);
        return ResponseEntity.status(HttpStatus.OK).body("Campus removed");
    }
}


