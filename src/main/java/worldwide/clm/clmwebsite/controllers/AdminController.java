package worldwide.clm.clmwebsite.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.AdminInvitationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.services.adminServices.AdminService;

@RestController
@RequestMapping("/clmWebsite/api/v1/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/sendInvitationLink")
    public ResponseEntity<ApiResponse> sendInvitationLink(@Valid @RequestBody AdminInvitationRequest request) throws ClmException {
        return ResponseEntity.ok().body(adminService.sendInvitationLink(request));
    }

    @GetMapping("acceptInvitation/{encryptedLink}")
    public ResponseEntity<String> acceptInvitation(@PathVariable String encryptedLink) throws ClmException {
        return ResponseEntity.ok().body(adminService.acceptInvitation(encryptedLink));
    }
}
