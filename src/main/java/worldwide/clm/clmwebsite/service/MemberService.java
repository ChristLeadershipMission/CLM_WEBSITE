package worldwide.clm.clmwebsite.service;

import worldwide.clm.clmwebsite.data.models.Member;

import java.util.Optional;

public interface MemberService {
	Member saveMembers(Member member);
	
	Member findMemberByEmail(String email);
	
	Optional<Member> findMemberById(Long userId);
}
