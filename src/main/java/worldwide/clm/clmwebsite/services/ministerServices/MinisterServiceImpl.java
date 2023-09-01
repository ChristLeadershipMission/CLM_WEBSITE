package worldwide.clm.clmwebsite.services.ministerServices;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.data.repositories.MinisterRepository;
import worldwide.clm.clmwebsite.dto.response.BioDataResponse;
import worldwide.clm.clmwebsite.dto.response.MemberResponse;
import worldwide.clm.clmwebsite.dto.response.MinisterResponse;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import static worldwide.clm.clmwebsite.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@Service
@AllArgsConstructor
public class MinisterServiceImpl implements MinisterService{
    private final MinisterRepository ministerRepository;
    private final ModelMapper modelMapper;
    @Override
    public MinisterResponse findByEmail(String emailAddress) throws UserNotFoundException {
        Minister foundMinister = ministerRepository.findByBioData_EmailAddress(emailAddress).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
        BioDataResponse bioDataResponse = modelMapper.map(foundMinister.getBioData(), BioDataResponse.class);
        MinisterResponse ministerResponse = modelMapper.map(foundMinister, MinisterResponse.class);
        ministerResponse.setBioData(bioDataResponse);
        return ministerResponse;
    }
}
