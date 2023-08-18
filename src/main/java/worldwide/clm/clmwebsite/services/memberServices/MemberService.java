package worldwide.clm.clmwebsite.services;

import worldwide.clm.clmwebsite.data.models.Member;

import java.util.Optional;

public interface MemberService {
	Member register(Member member);
	
	Member findMemberByEmail(String email);
	
	Optional<Member> findMemberById(Long userId);
}
