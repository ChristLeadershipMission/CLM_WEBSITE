package worldwide.clm.clmwebsite.services.memberServices;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.repositories.MemberRepository;
import worldwide.clm.clmwebsite.dto.response.BioDataResponse;
import worldwide.clm.clmwebsite.dto.response.MemberResponse;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.Optional;

import static worldwide.clm.clmwebsite.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberRepository repository;
	private final ModelMapper modelMapper;

	
	@Override
	public Member register(Member member) {
		return repository.save (member);
	}
	
	@Override
	public MemberResponse findByEmail(String email) throws UserNotFoundException {
		Member foundMember = repository.findByBioData_EmailAddress(email).orElseThrow(
				()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, email))
		);
		BioDataResponse bioDataResponse = modelMapper.map(foundMember.getBioData(), BioDataResponse.class);
		MemberResponse memberResponse = modelMapper.map(foundMember, MemberResponse.class);
		memberResponse.setBioData(bioDataResponse);
		return memberResponse;
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
