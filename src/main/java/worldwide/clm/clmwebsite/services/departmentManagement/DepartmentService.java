package worldwide.clm.clmwebsite.services.departmentManagement;

import worldwide.clm.clmwebsite.data.models.Department;
import worldwide.clm.clmwebsite.dto.request.DepartmentDto;
import worldwide.clm.clmwebsite.dto.request.DepartmentUpdateDto;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;

import java.util.List;

public interface DepartmentService {

    ApiResponse createDepartment (DepartmentDto request);
    ApiResponse updateDepartment (Long id, DepartmentUpdateDto request) throws ClmException;
    void deleteDepartment (String name);
    Department getDepartmentById (Long id);
    Department getDepartmentByName (String name);
    List<Department> getAllDepartments ();
}
