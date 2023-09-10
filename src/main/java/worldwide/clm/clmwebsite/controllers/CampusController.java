package worldwide.clm.clmwebsite.controllers;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.Contact;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.services.campusServices.CampusService;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clmWebsite/api/v1/campus/")
@RequiredArgsConstructor
@Server(
        description = "Local Testing",
        url = "http://localhost:8081"
)
public class CampusController {

    private final CampusService campusService;

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
    public ResponseEntity<ApiResponse> createCampus(@RequestBody CampusCreationRequest campusCreationRequest) throws CampusAlreadyExistsException {
            campusService.createCampus(campusCreationRequest);
            ApiResponse apiResponse = ResponseUtils.getCreatedMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CampusDetailsResponse.class))
    )

    @PatchMapping(value = "updateCampus/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateCampusDetails
            (@PathVariable Long id, @RequestBody JsonPatch campusUpdateRequest){
        CampusDetailsResponse updatedCampus = campusService.updateCampusDetails(id, campusUpdateRequest);
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
            responseCode = "302",
            description = "Campus found and returned",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campus not found")
    @GetMapping("getCampusById/{id}")
    public ResponseEntity<?> getCampusById(@PathVariable Long id)  {
            Optional<Campus> campus = campusService.findCampusById(id);
            return new ResponseEntity<>(campus, HttpStatus.FOUND);
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
    public ResponseEntity<?> getCampusByName(@PathVariable String name)  {
            Optional<Campus> campus = campusService.findCampusByName(name);
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
            responseCode = "302",
            description = "Campuses found and returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campuses not found")
    @GetMapping("allCampuses")
    public ResponseEntity<?> getAllCampuses() {
            List<Campus> campuses = campusService.findAllCampuses();
            return new ResponseEntity<>(campuses, HttpStatus.FOUND);
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
        campusService.removeCampus(id);
        return ResponseEntity.status(HttpStatus.OK).body("Campus removed");
    }
}


