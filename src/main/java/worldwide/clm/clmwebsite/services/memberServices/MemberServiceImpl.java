package worldwide.clm.clmwebsite.services.memberServices;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.repositories.MemberRepository;

import java.util.Optional;

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
	
	@Override
	public Optional<Member> findMemberById(Long userId) {
		return repository.findById (userId);
	}

	@Override
	public void saveMembers(Member member) {
		repository.save(member);
	}
}
