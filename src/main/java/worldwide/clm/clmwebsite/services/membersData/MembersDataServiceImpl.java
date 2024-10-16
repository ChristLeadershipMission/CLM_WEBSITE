package worldwide.clm.clmwebsite.services.membersData;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.MembersData;
import worldwide.clm.clmwebsite.data.repositories.MembersDataRepository;
import worldwide.clm.clmwebsite.dto.request.MemberUpdateRequest;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static worldwide.clm.clmwebsite.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MembersDataServiceImpl implements MembersDataService {
    private final MembersDataRepository membersDataRepository;
    private final ModelMapper modelMapper;


    @Override
    public void updateMemberData(MemberUpdateRequest memberUpdateRequest) throws UserNotFoundException {
        System.out.println("Member update request: " + memberUpdateRequest);
		if (memberUpdateRequest.getId() == null) {
			ModelMapper mapper = new ModelMapper();
			MembersData membersData = mapper.map(memberUpdateRequest, MembersData.class);
			membersDataRepository.save(membersData);
		}
		else {
			Optional<MembersData> member = membersDataRepository.findById(memberUpdateRequest.getId());
            if (member.isPresent()) {
                MembersData membersData = member.get();
                membersData.setFirstname(memberUpdateRequest.getFirstname());
                membersData.setLastname(memberUpdateRequest.getLastname());
                membersData.setEmailAddress(memberUpdateRequest.getEmailAddress());
                membersData.setPhoneNumber(memberUpdateRequest.getPhoneNumber());
                membersData.setMaritalStatus(memberUpdateRequest.getMaritalStatus());
                membersData.setDob(memberUpdateRequest.getDob());
                membersData.setPicture(memberUpdateRequest.getPicture());
                membersData.setLocation(memberUpdateRequest.getLocation());
                membersData.setGender(memberUpdateRequest.getGender());
                System.out.println("Member to be updated: " + memberUpdateRequest);
                membersDataRepository.save(membersData);
            } else {
                throw new UserNotFoundException("User not found with id: " + memberUpdateRequest.getId());
			}
		}
    }

    @Override
    public List<MembersData> findAll() {
        return membersDataRepository.findAll();
    }

    @Override
    public MembersData search(String searchParam) throws UserNotFoundException {
        System.out.println("Search parameters: " + searchParam);
        searchParam = searchParam.trim();
        if (isValidEmail(searchParam)) {
            Optional<MembersData> member = membersDataRepository.findByEmailAddress(searchParam);
            return finalResponse(member, searchParam);
        }
        String phoneNumber = "";
        if (searchParam.startsWith("+2340") || searchParam.startsWith("+234")) {
            System.out.println("Phone number starts with +234");
            String[] splitNumber = searchParam.split("\\+234");
            System.out.println("Splitted number: " + Arrays.toString(splitNumber));
            phoneNumber = splitNumber[1];
        }
        else if (searchParam.startsWith("234")) {
            System.out.println("Phone number starts with 234");
            String[] splitNumber = searchParam.split("234");
            System.out.println("Splitted number: " + Arrays.toString(splitNumber));
            phoneNumber = splitNumber[1];
        } else if (searchParam.startsWith("0")) {
            System.out.println("Phone number starts with 0");
			phoneNumber = searchParam;
		}
        System.err.println("Phone number: " + phoneNumber);
		Optional<MembersData> member = membersDataRepository.findByPhoneNumber(phoneNumber);
        return finalResponse(member, searchParam);
    }

    private MembersData finalResponse(Optional<MembersData> member, String searchParam) throws UserNotFoundException {
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new UserNotFoundException(String.format("USER_WITH_EMAIL/NUMBER_NOT_FOUND", searchParam));
        }
    }

    public static boolean isValidEmail(String input) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = emailPattern.matcher(input);
        return emailMatcher.matches();
    }
}
