package worldwide.clm.clmwebsite.services.memberServices;

import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.dto.response.MemberResponse;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.Optional;

public interface MemberService {
	Member register(Member member);
	
	MemberResponse findByEmail(String email) throws UserNotFoundException;
	
	Optional<Member> findMemberById(Long userId);

	void saveMembers(Member member);

}
