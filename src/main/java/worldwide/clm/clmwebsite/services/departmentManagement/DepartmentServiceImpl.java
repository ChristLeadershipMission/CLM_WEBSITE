package worldwide.clm.clmwebsite.services.departmentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Department;
import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.data.repositories.DepartmentRepository;
import worldwide.clm.clmwebsite.data.repositories.MinisterRepository;
import worldwide.clm.clmwebsite.dto.request.DepartmentDto;
import worldwide.clm.clmwebsite.dto.request.DepartmentUpdateDto;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.utils.ResponseUtils;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;
    private final MinisterRepository repository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, MinisterRepository repository) {
        this.departmentRepository = departmentRepository;
        this.repository = repository;
    }

    @Override
    public ApiResponse createDepartment(DepartmentDto request) {
        Minister checkMinister = repository.findById(request.getMinisterInCharge()).orElseThrow(() ->
                new UsernameNotFoundException("Minister with " + request.getMinisterInCharge() + " not found"));
        Department createDepartment = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .address(request.getAddress())
                .groupPicture(request.getGroupPicture())
                .createdDate(ZonedDateTime.now())
                .ministerInCharge(checkMinister.getId())
                .build();
        var savedDepartment = departmentRepository.save(createDepartment);
        return ResponseUtils.created(savedDepartment);
    }

    @Override
    public ApiResponse updateDepartment(Long id, DepartmentUpdateDto request) throws ClmException {
        Department checkDepartment = getDepartmentById(id);
        if (checkDepartment == null){
            throw new ClmException("Department not found");
        }
        mapUpdate(checkDepartment, request);
        departmentRepository.save(checkDepartment);
        return ResponseUtils.okResponse();
    }

    private void mapUpdate(Department checkDepartment, DepartmentUpdateDto request) {
        checkDepartment.setName(request.getName());
        checkDepartment.setDescription(request.getDescription());
        checkDepartment.setMinisterInCharge(request.getMinisterInCharge());
        checkDepartment.setUpdatedDate(ZonedDateTime.now());
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow
                (() -> new UsernameNotFoundException("No such department"));
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
