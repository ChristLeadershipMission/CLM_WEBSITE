package worldwide.clm.clmwebsite.services.eventServices.eventUpdate;

import worldwide.clm.clmwebsite.dto.request.EventUpdateRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;

public interface EventUpdateService {
    ApiResponse updateEventInfo(String eventName, EventUpdateRequest eventUpdateRequest);
}
