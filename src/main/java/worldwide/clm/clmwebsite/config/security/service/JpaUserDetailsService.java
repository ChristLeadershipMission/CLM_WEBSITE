package worldwide.clm.clmwebsite.config.security.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import worldwide.clm.clmwebsite.config.security.user.SecureUser;
import worldwide.clm.clmwebsite.data.models.Member;
import worldwide.clm.clmwebsite.data.repositories.MemberRepository;
import worldwide.clm.clmwebsite.enums.Role;

import java.util.List;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member user = repository.findMemberByEmail (username);
        if (user == null) {
            throw new UsernameNotFoundException("Not found");
        }
        return SecureUser.builder()
                .user(user)
                .roles (List.of (Role.USERS, Role.ADMIN))
                .build();
    }
}
