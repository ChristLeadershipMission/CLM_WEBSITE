package worldwide.clm.clmwebsite.services.dashboardService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.dto.response.AdminDashboardStatistics;
import worldwide.clm.clmwebsite.services.campusServices.CampusService;
import worldwide.clm.clmwebsite.services.departmentManagement.DepartmentService;
import worldwide.clm.clmwebsite.services.eventServices.EventService;
import worldwide.clm.clmwebsite.services.ministerServices.MinisterService;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService{
    private final MinisterService ministerService;
    private final CampusService campusService;
    private final EventService eventService;
    private final DepartmentService departmentService;
    @Override
    public AdminDashboardStatistics getAdminDashboardStatistics() {
        return AdminDashboardStatistics.builder()
                .numberOfCampuses(campusService.getCount())
                .numberOfDepartments(departmentService.getCount())
                .numberOfMinisters(ministerService.getCount())
                .numberOfUpcomingEvents(eventService.getCount())
                .build();
    }
}
