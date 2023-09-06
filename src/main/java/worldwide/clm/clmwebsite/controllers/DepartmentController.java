package worldwide.clm.clmwebsite.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.data.models.Department;
import worldwide.clm.clmwebsite.dto.request.DepartmentDto;
import worldwide.clm.clmwebsite.dto.request.DepartmentUpdateDto;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.services.departmentManagement.DepartmentService;

import java.util.List;

@RequestMapping("api/v1/department/")
@RestController
public class DepartmentController {

    private final DepartmentService service;

    @Autowired
    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createDepartment(@Valid @RequestBody DepartmentDto request) {
        var response = service.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("update/{id}")
    public ResponseEntity<ApiResponse> updateDepartment(@PathVariable Long id, @RequestBody DepartmentUpdateDto request) throws ClmException {
        var response = service.updateDepartment(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartment() {
        var response = service.getAllDepartments();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("by-id/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        var response = service.getDepartmentById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("by-name/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable String name) {
        var response = service.getDepartmentByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
