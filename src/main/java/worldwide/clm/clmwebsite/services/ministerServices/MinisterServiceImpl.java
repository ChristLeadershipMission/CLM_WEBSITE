package worldwide.clm.clmwebsite.services.ministerServices;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.BioData;
import worldwide.clm.clmwebsite.data.models.Campus;
import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.data.repositories.MinisterRepository;
import worldwide.clm.clmwebsite.dto.request.MinisterRegistrationRequest;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import java.util.List;

import static worldwide.clm.clmwebsite.common.Message.*;

@Service
@AllArgsConstructor
public class MinisterServiceImpl implements MinisterService{
    private final MinisterRepository ministerRepository;
    private final ModelMapper modelMapper;
    @Override
    public Minister findByEmail(String emailAddress) throws UserNotFoundException {
        return ministerRepository.findByEmailAddress    (emailAddress).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
    }

    @Override
    public Minister findById(Long id) throws UserNotFoundException {
        return ministerRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException(String.format(MINISTER_WITH_ID_NOT_FOUND, id))
        );
    }

    @Override
    public List<Minister> findAll() throws UserNotFoundException {
        return ministerRepository.findAll();
    }

    @Override
    public Minister createMinister(MinisterRegistrationRequest ministerRegistrationRequest) {
        Minister minister = modelMapper.map(ministerRegistrationRequest, Minister.class);
        return ministerRepository.save(minister);
    }

    @Override
    public Minister updateMinisterDetails(Long id, MinisterRegistrationRequest ministerUpdateRequest) {
        Minister foundMinister = ministerRepository.getReferenceById(id);
        if (ministerUpdateRequest.getEmailAddress() != null && ministerUpdateRequest.getEmailAddress() != ""){
            foundMinister.setEmailAddress(ministerUpdateRequest.getEmailAddress());
        }if (ministerUpdateRequest.getFirstName() != null && ministerUpdateRequest.getFirstName() != ""){
            foundMinister.setFirstName(ministerUpdateRequest.getFirstName());
        }if (ministerUpdateRequest.getLastName() != null && ministerUpdateRequest.getLastName() != ""){
            foundMinister.setLastName(ministerUpdateRequest.getLastName());
        }if (ministerUpdateRequest.getPhoneNumber() != null && ministerUpdateRequest.getPhoneNumber() != ""){
            foundMinister.setPhoneNumber(ministerUpdateRequest.getPhoneNumber());
        }if (ministerUpdateRequest.getProfilePicture() != null && ministerUpdateRequest.getProfilePicture() != ""){
            foundMinister.setProfilePicture(ministerUpdateRequest.getProfilePicture());
        }if (ministerUpdateRequest.getPortfolio() != null && ministerUpdateRequest.getPortfolio() != ""){
            foundMinister.setPortfolio(ministerUpdateRequest.getPortfolio());
        }
        return ministerRepository.save(foundMinister);
    }

    @Override
    public void deleteMinister(Long id) {
        ministerRepository.deleteById(id);
    }

    @Override
    public Long getCount() {
        return ministerRepository.count();
    }

    @Override
    public List<Minister> searchMinistersByName(String name) {
        return ministerRepository.searchAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
    }
}
