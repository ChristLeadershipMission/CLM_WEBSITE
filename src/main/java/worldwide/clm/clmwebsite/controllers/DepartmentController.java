package worldwide.clm.clmwebsite.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.data.models.Department;
import worldwide.clm.clmwebsite.dto.request.DepartmentCreationRequest;
import worldwide.clm.clmwebsite.dto.request.DepartmentUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
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

    @PostMapping("createDepartment")
    public ResponseEntity<ApiResponse> createDepartment(@Valid @RequestBody DepartmentCreationRequest request) {
        var response = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateDepartment(@PathVariable Long id, @RequestBody DepartmentUpdateRequest request) throws ClmException {
        var response = departmentService.updateDepartment(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getAllDepartment")
    public ResponseEntity<List<Department>> getAllDepartment() {
        var response = departmentService.getAllDepartments();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("getDepartmentById/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        var response = departmentService.getDepartmentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("getDepartmentByName/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        var response = departmentService.getDepartmentByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("deleteDepartmentByName/{name}")
    public ResponseEntity<String> deleteDepartmentByName(@PathVariable String name){
        departmentService.deleteDepartment(name);
        return ResponseEntity.status(HttpStatus.OK).body("Department deleted successfully");
    }
}
