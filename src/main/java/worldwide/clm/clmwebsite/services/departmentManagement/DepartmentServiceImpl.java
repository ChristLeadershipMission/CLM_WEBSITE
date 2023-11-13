package worldwide.clm.clmwebsite.services.departmentManagement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Department;
import worldwide.clm.clmwebsite.data.repositories.DepartmentRepository;
import worldwide.clm.clmwebsite.dto.request.DepartmentCreationRequest;
import worldwide.clm.clmwebsite.dto.request.DepartmentUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.DepartmentAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.DepartmentNotFoundException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.time.LocalDateTime;
import java.util.List;

import static worldwide.clm.clmwebsite.common.Message.DEPARTMENT_WITH_ID_NOT_FOUND;
import static worldwide.clm.clmwebsite.common.Message.DEPARTMENT_WITH_NAME_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;
    private final MinisterService ministerService;


    @Override
    public ApiResponse createDepartment(DepartmentCreationRequest request) throws UserNotFoundException, DepartmentAlreadyExistsException {
        MinisterResponse foundMinister = ministerService.findById(request.getMinisterInChargeId());
        validateDuplicateExistence(request);
        Department createDepartment = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .groupPicture(request.getGroupPicture())
                .createdAt(LocalDateTime.now())
                .ministerInChargeId(foundMinister.getId())
                .build();
        var savedDepartment = departmentRepository.save(createDepartment);
        return ResponseUtils.created(savedDepartment);
    }

    private void validateDuplicateExistence(DepartmentCreationRequest request) throws DepartmentAlreadyExistsException {
        boolean departmentAlreadyExist = getDepartmentByName(request.getName()) != null;
        if (departmentAlreadyExist)
            throw new DepartmentAlreadyExistsException(String.format(DEPARTMENT_WITH_NAME_ALREADY_EXISTS, request.getName()));
    }

    @Override
    public ApiResponse updateDepartment(Long id, DepartmentUpdateRequest request) throws ClmException {
        Department checkDepartment = getDepartmentById(id);
        mapUpdate(checkDepartment, request);
        departmentRepository.save(checkDepartment);
        return ResponseUtils.okResponse();
    }

    private void mapUpdate(Department department, DepartmentUpdateRequest request) {
        if (request.getName() != null) department.setName(request.getName());
        if (request.getDescription() != null) department.setDescription(request.getDescription());
        if (request.getMinisterInCharge() != null) department.setMinisterInChargeId(request.getMinisterInCharge());
        department.setUpdatedAt(LocalDateTime.now());
    }

    @Override
    public void deleteDepartment(String name) {
        departmentRepository.deleteByName(name);
    }

    @Override
    public Department getDepartmentById(Long id) throws DepartmentNotFoundException {
        return departmentRepository.findById(id).orElseThrow
                (() -> new DepartmentNotFoundException(String.format(DEPARTMENT_WITH_ID_NOT_FOUND, id)));
    }

    @Override
    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
