package worldwide.clm.clmwebsite.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import worldwide.clm.clmwebsite.dto.request.AdminInvitationRequest;
import worldwide.clm.clmwebsite.dto.request.AdminSignupRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;
import worldwide.clm.clmwebsite.services.adminServices.AdminService;
import worldwide.clm.clmwebsite.services.authenticationServices.AuthenticationService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import static worldwide.clm.clmwebsite.common.Message.FAILED_TO_GET_ACTIVATION_LINK;
import static worldwide.clm.clmwebsite.utils.AppUtils.ADMIN_INVITATION_HTML_TEMPLATE_LOCATION;
import static worldwide.clm.clmwebsite.utils.AppUtils.SUCCESSFUL_REGISTRATION_HTML_TEMPLATE_LOCATION;

@RestController
@RequestMapping("/clmWebsite/api/v1/admin/")
@RequiredArgsConstructor
public class AdminController {

    private final AuthenticationService authenticationService;
    private final AdminService adminService;

    @PostMapping("registration")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody AdminSignupRequest request) throws UserAlreadyExistsException, UserNotFoundException {
        return new ResponseEntity<>(authenticationService.signup(request), HttpStatus.CREATED);
    }

    @PostMapping("/sendInvitationLink/{emailAddress}")
    public ResponseEntity<ApiResponse> sendInvitationLink(@Valid @RequestBody AdminInvitationRequest request) throws ClmException {
        return ResponseEntity.ok().body(adminService.sendInvitationLink(request));
    }

    @GetMapping("acceptInvitation/{encryptedLink}")
    public ResponseEntity<String> acceptInvitation(@PathVariable String encryptedLink) throws ClmException {
        return ResponseEntity.ok().body(adminService.acceptInvitation(encryptedLink));
    }
}
