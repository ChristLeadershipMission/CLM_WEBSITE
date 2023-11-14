package worldwide.clm.clmwebsite.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AdminDashboardStatistics {
    private Long numberOfCampuses;
    private Long numberOfMinisters;
    private Long numberOfUpcomingEvents;
    private Long numberOfDepartments;
}
