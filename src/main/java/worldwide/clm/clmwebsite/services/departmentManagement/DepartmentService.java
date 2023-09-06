package worldwide.clm.clmwebsite.services.departmentManagement;

import worldwide.clm.clmwebsite.data.models.Department;
import worldwide.clm.clmwebsite.dto.request.DepartmentCreationRequest;
import worldwide.clm.clmwebsite.dto.request.DepartmentUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.DepartmentAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.DepartmentNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.List;

public interface DepartmentService {

    ApiResponse createDepartment (DepartmentCreationRequest request) throws UserNotFoundException, DepartmentAlreadyExistsException;
    ApiResponse updateDepartment (Long id, DepartmentUpdateRequest request) throws ClmException;
    void deleteDepartment (String name);
    Department getDepartmentById (Long id) throws DepartmentNotFoundException;
    Department getDepartmentByName (String name);
    List<Department> getAllDepartments ();
}
