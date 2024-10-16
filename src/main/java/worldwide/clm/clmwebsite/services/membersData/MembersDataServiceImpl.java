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
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class MembersDataServiceImpl implements MembersDataService {
    private final MembersDataRepository membersDataRepository;
    private final ModelMapper modelMapper;


    @Override
    public void updateMemberData(MemberUpdateRequest memberUpdateRequest) throws UserNotFoundException {
        System.out.println("Member update request: " + memberUpdateRequest);
        if (memberUpdateRequest.getId() == null) {
            if (membersDataRepository.findByPhoneNumber(memberUpdateRequest.getPhoneNumber()).isPresent()) {
                throw new UserNotFoundException("Phone number already exists");
            } else if (membersDataRepository.findByEmailAddress(memberUpdateRequest.getEmailAddress().toLowerCase(Locale.ROOT)).isPresent()) {
                throw new UserNotFoundException("Email already exists");
            }
            ModelMapper mapper = new ModelMapper();
            MembersData membersData = mapper.map(memberUpdateRequest, MembersData.class);
            membersData.setEmailAddress(memberUpdateRequest.getEmailAddress().toLowerCase(Locale.ROOT));
            System.err.println("Member update request: " + membersData);
            membersDataRepository.save(membersData);
        } else {
            Optional<MembersData> member = membersDataRepository.findById(memberUpdateRequest.getId());
            if (member.isPresent()) {
                MembersData membersData = member.get();
                if (memberUpdateRequest.getFirstname() != null && !memberUpdateRequest.getFirstname().isBlank()) {
                    membersData.setFirstname(memberUpdateRequest.getFirstname());
                }
                if (memberUpdateRequest.getLastname() != null && !memberUpdateRequest.getLastname().isBlank()) {
                    membersData.setLastname(memberUpdateRequest.getLastname());
                }
                if (memberUpdateRequest.getEmailAddress() != null && !memberUpdateRequest.getEmailAddress().isBlank()) {
                    membersData.setEmailAddress(memberUpdateRequest.getEmailAddress().toLowerCase(Locale.ROOT));
                }
                if (memberUpdateRequest.getPhoneNumber() != null && !memberUpdateRequest.getPhoneNumber().isBlank()) {
                    membersData.setPhoneNumber(memberUpdateRequest.getPhoneNumber());
                }
                if (memberUpdateRequest.getMaritalStatus() != null && !memberUpdateRequest.getMaritalStatus().isBlank()) {
                    membersData.setMaritalStatus(memberUpdateRequest.getMaritalStatus());
                }
                if (memberUpdateRequest.getDob() != null){
                    membersData.setDob(memberUpdateRequest.getDob());
                }
                if (memberUpdateRequest.getPicture() != null && !memberUpdateRequest.getPicture().isBlank()) {
                    membersData.setPicture(memberUpdateRequest.getPicture());
                }
                if (memberUpdateRequest.getLocation() != null && !memberUpdateRequest.getLocation().isBlank()) {
                    membersData.setLocation(memberUpdateRequest.getLocation());
                }
                if (memberUpdateRequest.getGender() != null && !memberUpdateRequest.getGender().isBlank()) {
                    membersData.setGender(memberUpdateRequest.getGender());
                }
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
            return finalResponse(member);
        }
        String phoneNumber = "";
        if (searchParam.startsWith("+2340") || searchParam.startsWith("+234")) {
            System.out.println("Phone number starts with +234");
            String[] splitNumber = searchParam.split("\\+234");
            System.out.println("Splitted number: " + Arrays.toString(splitNumber));
            phoneNumber = splitNumber[1].startsWith("0") ? splitNumber[1] : "0" + splitNumber[1];
        } else if (searchParam.startsWith("234")) {
            System.out.println("Phone number starts with 234");
            String[] splitNumber = searchParam.split("234");
            System.out.println("Splitted number: " + Arrays.toString(splitNumber));
            phoneNumber = splitNumber[1].startsWith("0") ? splitNumber[1] : "0" + splitNumber[1];
        } else if (searchParam.startsWith("0")) {
            System.out.println("Phone number starts with 0");
            phoneNumber = searchParam;
        } else phoneNumber = searchParam.startsWith("0") ? searchParam : "0" + searchParam;
        System.err.println("Phone number: " + phoneNumber);
        Optional<MembersData> member = membersDataRepository.findByPhoneNumber(phoneNumber);
        return finalResponse(member);
    }

    private MembersData finalResponse(Optional<MembersData> member) {
        return member.orElseGet(MembersData::new);
    }

    public static boolean isValidEmail(String input) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern emailPattern = Pattern.compile(EMAIL_REGEX);
        Matcher emailMatcher = emailPattern.matcher(input);
        return emailMatcher.matches();
    }
}
