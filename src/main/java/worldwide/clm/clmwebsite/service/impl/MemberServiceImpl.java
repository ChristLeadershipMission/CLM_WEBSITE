package worldwide.clm.clmwebsite.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.repositories.MemberRepository;
import worldwide.clm.clmwebsite.service.MemberService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository repository;
	
	
	@Override
	public Member saveMembers(Member member) {
		return repository.save (member);
	}
	
	@Override
	public Member findMemberByEmail(String email) {
		return repository.findMemberByEmail (email);
	}
	
	@Override
	public Optional<Member> findMemberById(Long userId) {
		return repository.findById (userId);
	}
}
