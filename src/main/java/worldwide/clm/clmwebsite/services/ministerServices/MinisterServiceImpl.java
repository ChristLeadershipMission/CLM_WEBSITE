package worldwide.clm.clmwebsite.services.ministerServices;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.BioData;
import worldwide.clm.clmwebsite.data.models.Minister;
import worldwide.clm.clmwebsite.data.repositories.MinisterRepository;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import static worldwide.clm.clmwebsite.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@Service
@AllArgsConstructor
public class MinisterServiceImpl implements MinisterService{
    private final MinisterRepository ministerRepository;
    private final ModelMapper modelMapper;
    @Override
    public Minister findByEmail(String emailAddress) throws UserNotFoundException {
        return ministerRepository.findByBioData_EmailAddress(emailAddress).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, emailAddress))
        );
    }

    @Override
    public Minister findById(Long id) throws UserNotFoundException {
        return ministerRepository.findById(id).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, id))
        );
    }
}
