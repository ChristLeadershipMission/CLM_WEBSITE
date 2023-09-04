package worldwide.clm.clmwebsite.services.bioDataServices;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.data.models.BioData;
import worldwide.clm.clmwebsite.data.repositories.BioDataRepository;
import worldwide.clm.clmwebsite.dto.request.ResetPasswordRequest;
import worldwide.clm.clmwebsite.dto.response.ApiResponse;
import worldwide.clm.clmwebsite.dto.response.BioDataResponse;
import worldwide.clm.clmwebsite.exception.UserAlreadyExistsException;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import static worldwide.clm.clmwebsite.common.Message.ACCOUNT_ALREADY_EXIST;
import static worldwide.clm.clmwebsite.common.Message.USER_WITH_EMAIL_NOT_FOUND;
import static worldwide.clm.clmwebsite.utils.ResponseUtils.passwordResetResponse;

@Service
@AllArgsConstructor
public class BioDataServiceImpl implements BioDataService{
    private final BioDataRepository bioDataRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public BioDataResponse findByEmail(String email) throws UserNotFoundException {
        BioData foundBioData = bioDataRepository.findByEmailAddress(email).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, email))
        );
        return modelMapper.map(foundBioData, BioDataResponse.class);
    }

    @Override
    public void validateDuplicateUserExistence(String emailAddress) throws UserAlreadyExistsException {
        var bioData = bioDataRepository.findByEmailAddress(emailAddress);
        boolean accountWithGivenEmailAlreadyExist = bioData.isPresent();
        if (accountWithGivenEmailAlreadyExist) throw new UserAlreadyExistsException(String.format(ACCOUNT_ALREADY_EXIST, emailAddress));
    }

    @Override
    public ApiResponse resetPassword(String email, String newPassword) throws UserNotFoundException {
        BioData foundBioData = bioDataRepository.findByEmailAddress(email).orElseThrow(
                ()-> new UserNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, email))
        );
        foundBioData.setPassword(newPassword);
        bioDataRepository.save(foundBioData);
        return passwordResetResponse();
    }

    @Override
    public boolean passwordMatch(String emailAddress, String oldPassword) {
        BioData foundBioData = bioDataRepository.findByEmailAddress(emailAddress).get();
        return passwordEncoder.matches(oldPassword, foundBioData.getPassword());
    }
}
