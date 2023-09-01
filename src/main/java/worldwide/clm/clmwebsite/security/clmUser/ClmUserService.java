package worldwide.clm.clmwebsite.security.clmUser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import worldwide.clm.clmwebsite.data.models.BioData;
import worldwide.clm.clmwebsite.data.repositories.BioDataRepository;
import worldwide.clm.clmwebsite.exception.UserNotFoundException;

import static worldwide.clm.clmwebsite.common.Message.USER_WITH_EMAIL_NOT_FOUND;

@AllArgsConstructor
@Repository
public class ClmUserService implements UserDetailsService {
    private final BioDataRepository bioDataRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BioData user = bioDataRepository.findByEmailAddress(username).orElseThrow(()->new UsernameNotFoundException(
                String.format(USER_WITH_EMAIL_NOT_FOUND, username)
        ));
        return new ClmUser(user);
    }
}
