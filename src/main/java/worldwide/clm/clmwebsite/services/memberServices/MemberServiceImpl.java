package worldwide.clm.clmwebsite.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.repositories.MemberRepository;
import worldwide.clm.clmwebsite.services.MemberService;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository repository;
	
	
	@Override
	public Member register(Member member) {
		return repository.save (member);
	}
	
	@Override
	public Member findMemberByEmail(String email) {
		return repository.findMemberByEmail (email);
	}
}
