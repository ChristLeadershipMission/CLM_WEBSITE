package worldwide.clm.clmwebsite.controllers;

import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.CampusDetailsResponse;
import worldwide.clm.clmwebsite.exception.BusinessLogicException;
import worldwide.clm.clmwebsite.exception.CampusAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.CampusNotFoundException;
import worldwide.clm.clmwebsite.services.campusServices.CampusService;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("clmcampus")
@RequiredArgsConstructor
public class CampusController {
    private final CampusService campusService;

    @PostMapping("create_campus")
    public ResponseEntity<ApiResponse> createCampus(@RequestBody CampusCreationRequest campusCreationRequest) throws BusinessLogicException {
        try {
            campusService.createCampus(campusCreationRequest);
            ApiResponse apiResponse = ResponseUtils.getCreatedMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        } catch (CampusAlreadyExistsException exception) {
            ApiResponse apiResponse = ResponseUtils.getFailureMessage();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(apiResponse);
        }
    }

    @PatchMapping(value = "campusUpdate/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateCampusDetails
            (@PathVariable Long id, @RequestBody JsonPatch campusUpdateRequest){
        CampusDetailsResponse updatedCampus = campusService.updateCampusDetails(id, campusUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCampus);
    }

    @GetMapping("getCampusById/{id}")
    public ResponseEntity<?> getCampusById(@PathVariable Long id) throws BusinessLogicException {
        try {
            Optional<Campus> campus = campusService.findCampusById(id);
            return new ResponseEntity<>(campus, HttpStatus.FOUND);
        } catch (CampusNotFoundException exception) {
            ApiResponse response = ResponseUtils.getCampusFailureResponse();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("getCampusByName/{name}")
    public ResponseEntity<?> getCampusByName(@PathVariable String name) throws BusinessLogicException {
        try {
            Optional<Campus> campus = campusService.findCampusByName(name);
            return new ResponseEntity<>(campus, HttpStatus.FOUND);
        } catch (CampusNotFoundException exception) {
            ApiResponse response = ResponseUtils.getCampusFailureResponse();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("allCampuses")
    public ResponseEntity<?> getAllCampuses() throws BusinessLogicException{
        try{
            List<Campus> campuses = campusService.findAllCampuses();
            return new ResponseEntity<>(campuses, HttpStatus.FOUND);
        } catch (CampusAlreadyExistsException exception){
            ApiResponse response = ResponseUtils.getCampusFailureResponse();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> removeCampus(@PathVariable Long id) throws BusinessLogicException {
        campusService.removeCampus(id);
        return ResponseEntity.status(HttpStatus.OK).body("Campus removed");
    }
}


