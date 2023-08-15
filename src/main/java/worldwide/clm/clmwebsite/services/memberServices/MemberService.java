package worldwide.clm.clmwebsite.services;

import worldwide.clm.clmwebsite.data.models.Member;

public interface MemberService {
	Member register(Member member);
	
	Member findMemberByEmail(String email);
}
