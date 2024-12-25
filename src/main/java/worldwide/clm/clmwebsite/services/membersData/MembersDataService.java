package worldwide.clm.clmwebsite.services.membersData;

import worldwide.clm.clmwebsite.data.models.MembersData;
import worldwide.clm.clmwebsite.dto.request.MemberUpdateRequest;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.List;

public interface MembersDataService {

	void updateMemberData(MemberUpdateRequest memberUpdateRequest) throws UserNotFoundException;

	List<MembersData> findAll();

	MembersData search(String searchParam) throws UserNotFoundException;

    void updateMembersDataList(List<MemberUpdateRequest> memberUpdateRequests);

	Object retrieveAllMembersPhoneNumber(String searchParam, String format);
}
