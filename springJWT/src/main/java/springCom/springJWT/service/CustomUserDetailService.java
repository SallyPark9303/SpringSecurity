package springCom.springJWT.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springCom.springJWT.dto.CustomUserDetails;
import springCom.springJWT.entity.UserEntity;
import springCom.springJWT.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    private  CustomUserDetailService(UserRepository userRepository){

        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByUsername(username);

        if(userData != null){
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
