package worldwide.clm.clmwebsite.services.eventServices.eventCreation;

import worldwide.clm.clmwebsite.dto.request.EventCreationRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;

public interface EventCreationService {

    ApiResponse createEvent(EventCreationRequest eventCreationRequest);

}
