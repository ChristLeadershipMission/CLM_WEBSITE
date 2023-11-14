package worldwide.clm.clmwebsite.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import worldwide.clm.clmwebsite.dto.response.AdminDashboardStatistics;
import worldwide.clm.clmwebsite.services.dashboardService.DashboardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clmWebsite/api/v1/dashboard/")
public class DashboardController {
    private final DashboardService dashboardService;
    @GetMapping("getAdminDashboardStatistics")
    public ResponseEntity<AdminDashboardStatistics> getAdminDashboardStatistics() {
        return ResponseEntity.ok(dashboardService.getAdminDashboardStatistics());
    }
}
