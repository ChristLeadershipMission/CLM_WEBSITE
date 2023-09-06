package worldwide.clm.clmwebsite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.data.models.Department;
import worldwide.clm.clmwebsite.dto.request.CampusCreationRequest;
import worldwide.clm.clmwebsite.dto.request.DepartmentCreationRequest;
import worldwide.clm.clmwebsite.dto.request.DepartmentUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.DepartmentAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.DepartmentNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.departmentManagement.DepartmentService;

import java.util.List;

@RequestMapping("/clmWebsite/api/v1/department/")
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.departmentService = service;
    }


    @Operation(
            summary = "Create Department",
            description = "An API handling department creation"
    )
    @Parameter(
            name = "DepartmentCreationRequest",
            description = "Containing all the necessary fields required to make a department exist",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = DepartmentCreationRequest.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Creation successful",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("createDepartment")
    public ResponseEntity<ApiResponse> createDepartment(@Valid @RequestBody DepartmentCreationRequest request) throws UserNotFoundException, DepartmentAlreadyExistsException {
        var response = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(
            summary = "Update Department details",
            description = "An API handling the update of department details"
    )
    @Parameter(
            name = "updateRequest",
            description = "Patch document containing updates for the campus.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = DepartmentUpdateRequest.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Department details updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ApiResponse.class))
    )
    @PatchMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateDepartment(@PathVariable Long id, @RequestBody DepartmentUpdateRequest request) throws ClmException {
        var response = departmentService.updateDepartment(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Get all departments",
            description = "Retrieve all departments"
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
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Department.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Departments not found")
    @GetMapping("getAllDepartment")
    public ResponseEntity<List<Department>> getAllDepartment() {
        var response = departmentService.getAllDepartments();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @Operation(
            summary = "Get Department by ID",
            description = "Retrieve department details by its unique identifier."
    )
    @Parameter(
            name = "id",
            description = "The unique identifier of the department to be retrieved.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = Long.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "302",
            description = "Department found and returned",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Department.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Department not found")
    @GetMapping("getDepartmentById/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) throws DepartmentNotFoundException {
        var response = departmentService.getDepartmentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Get Department by name",
            description = "Retrieve department details by its name"
    )
    @Parameter(
            name = "name",
            description = "The name of the department to be retrieved",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = Long.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "302",
            description = "Department found and returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Department.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Department not found")
    @GetMapping("getDepartmentByName/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        var response = departmentService.getDepartmentByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "Delete Department by name",
            description = "Delete department details by its name"
    )
    @Parameter(
            name = "name",
            description = "The name of the department to be retrieved and deleted",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = String.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Removal successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Department.class))
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Department not found")

    @DeleteMapping("deleteDepartmentByName/{name}")
    public ResponseEntity<String> deleteDepartmentByName(@PathVariable String name){
        departmentService.deleteDepartment(name);
        return ResponseEntity.status(HttpStatus.OK).body("Department deleted successfully");
    }
}
