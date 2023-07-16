package worldwide.clm.clmwebsite.service;

import worldwide.clm.clmwebsite.data.models.Member;

public interface MemberService {
	Member saveMembers(Member member);
	
	Member findMemberByEmail(String email);
}
