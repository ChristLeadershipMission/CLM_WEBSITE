package worldwide.clm.clmwebsite.controllers;

import com.github.fge.jsonpatch.JsonPatch;
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
public class CampusController {
    private final CampusService campusService;

    @PostMapping("createCampus")
    public ResponseEntity<ApiResponse> createCampus(@RequestBody CampusCreationRequest campusCreationRequest) throws CampusAlreadyExistsException {
            campusService.createCampus(campusCreationRequest);
            ApiResponse apiResponse = ResponseUtils.getCreatedMessage();
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PatchMapping(value = "updateCampus/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> updateCampusDetails
            (@PathVariable Long id, @RequestBody JsonPatch campusUpdateRequest){
        CampusDetailsResponse updatedCampus = campusService.updateCampusDetails(id, campusUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCampus);
    }

    @GetMapping("getCampusById/{id}")
    public ResponseEntity<?> getCampusById(@PathVariable Long id)  {
            Optional<Campus> campus = campusService.findCampusById(id);
            return new ResponseEntity<>(campus, HttpStatus.FOUND);
    }

    @GetMapping("getCampusByName/{name}")
    public ResponseEntity<?> getCampusByName(@PathVariable String name)  {
            Optional<Campus> campus = campusService.findCampusByName(name);
            return new ResponseEntity<>(campus, HttpStatus.FOUND);
    }

    @GetMapping("allCampuses")
    public ResponseEntity<?> getAllCampuses() {
            List<Campus> campuses = campusService.findAllCampuses();
            return new ResponseEntity<>(campuses, HttpStatus.FOUND);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> removeCampus(@PathVariable Long id) throws CampusNotFoundException {
        campusService.removeCampus(id);
        return ResponseEntity.status(HttpStatus.OK).body("Campus removed");
    }
}


