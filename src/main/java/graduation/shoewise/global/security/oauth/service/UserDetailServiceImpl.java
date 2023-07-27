package graduation.shoewise.global.security.oauth.service;

import graduation.shoewise.domain.user.User;
import graduation.shoewise.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    public UserDetailServiceImpl(UserRepository userRepository){this.userRepository = userRepository;}
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User principal = userRepository.findByEmail(userId).get();
        return UserPrincipal.create(principal);
    }
}
