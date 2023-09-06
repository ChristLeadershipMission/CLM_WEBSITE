package worldwide.clm.clmwebsite.services.adminServices;

import worldwide.clm.clmwebsite.data.models.Admin;
import worldwide.clm.clmwebsite.dto.request.AdminInvitationRequest;
import worldwide.clm.clmwebsite.dto.response.AdminResponse;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.exception.ClmException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

public interface AdminService {
    AdminResponse findByEmail(String emailAddress) throws UserNotFoundException;

    ApiResponse sendInvitationLink(AdminInvitationRequest request) throws ClmException;

    String acceptInvitation(String encryptedLink) throws ClmException;
}
