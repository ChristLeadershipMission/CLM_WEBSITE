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
import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.request.MinisterRegistrationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clmWebsite/api/v1/minister/")
@RequiredArgsConstructor
public class MinisterController {
    private final MinisterService ministerService;
    @Operation(
            summary = "Create Minister",
            description = "An API handling minister creation"
    )
    @Parameter(
            name = "MinisterRegistrationRequest",
            description = "Containing all the necessary fields required to register a minister",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = MinisterRegistrationRequest.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Creation successful",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("createMinister")
    public ResponseEntity<ApiResponse> createMinister(@RequestBody MinisterRegistrationRequest ministerRegistrationRequest) throws CampusAlreadyExistsException {
        ministerService.createMinister(ministerRegistrationRequest);
        ApiResponse apiResponse = ResponseUtils.getCreatedMessage();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(
            summary = "Get Minister by id",
            description = "Retrieve minister details by its id"
    )
    @Parameter(
            name = "id",
            description = "The id of the minister to be retrieved",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = String.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Minister found and returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campus not found")
    @GetMapping("getMinisterById/{id}")
    public ResponseEntity<?> getCampusByName(@PathVariable Long id) throws UserNotFoundException {
        Minister minister = ministerService.findById(id);
        return new ResponseEntity<>(minister, HttpStatus.OK);
    }

    @Operation(
            summary = "Get All existing Ministers",
            description = "Retrieve all ministers"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Ministers found and returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Campus.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Campus not found")
    @GetMapping("findAll")
    public ResponseEntity<?> findAll() throws UserNotFoundException {
        List<Minister> ministers = ministerService.findAll();
        return new ResponseEntity<>(ministers, HttpStatus.OK);
    }


    @Parameter(
            name = "ministerUpdateRequest",
            description = "JSON Patch document containing updates for the minister.",
            required = true,
            in = ParameterIn.PATH,
            content = @Content(mediaType = "application/json-patch+json",
                    schema = @Schema(implementation = JsonPatch.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Minister details updated successfully",
            content = @Content(mediaType = "application/json")
    )

    @PatchMapping(value = "updateMinister/{id}")
    public ResponseEntity<?> updateMinister
            (@PathVariable Long id, @RequestBody MinisterRegistrationRequest ministerUpdateRequest) {
        Minister updatedMinister = ministerService.updateMinisterDetails(id, ministerUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMinister);
    }

    @Operation(
            summary = "Delete Minister by id",
            description = "Delete minister by its unique identifier"
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Minister.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Minister not found")
    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteMinister(@PathVariable Long id) {
        ministerService.deleteMinister(id);
        return ResponseEntity.status(HttpStatus.OK).body("Minister removed");
    }

}
