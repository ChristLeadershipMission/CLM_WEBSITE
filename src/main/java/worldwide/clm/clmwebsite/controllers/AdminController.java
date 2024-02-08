package worldwide.clm.clmwebsite.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.AdminInvitationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.services.adminServices.AdminService;

@RestController
@RequestMapping("/clmWebsite/api/v1/admin/")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @GetMapping("log")
    public ResponseEntity<String> log() {
        log.info("Logging");
        return ResponseEntity.ok().body("Details logged in file");
    }
    @Operation(
            summary = "Send Invitation Link",
            description = "API for sending an invitation link to an admin."
    )
    @Parameter(
            name = "request",
            description = "Admin invitation request containing necessary details.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = AdminInvitationRequest.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Invitation link sent successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @PostMapping("/sendInvitationLink")
    public ResponseEntity<ApiResponse> sendInvitationLink(@Valid @RequestBody AdminInvitationRequest request) throws ClmException {
        return ResponseEntity.ok().body(adminService.sendInvitationLink(request));
    }

    @Operation(
            summary = "Accept Invitation Link",
            description = "API for accepting an invitation link to an admin."
    )
    @Parameter(
            name = "request",
            description = "Admin acceptance request containing necessary details.",
            required = true,
            in = ParameterIn.PATH,
            schema = @Schema(implementation = AdminInvitationRequest.class)
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "link accepted ",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiResponse.class))
    )
    @GetMapping("acceptInvitation/{encryptedLink}")
    public ResponseEntity<String> acceptInvitation(@PathVariable String encryptedLink) throws ClmException {
        return ResponseEntity.ok().body(adminService.acceptInvitation(encryptedLink));
    }
}
