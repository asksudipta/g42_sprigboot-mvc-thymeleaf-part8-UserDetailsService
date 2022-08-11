package se.lexicon.mvcthymeleaf.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import se.lexicon.mvcthymeleaf.model.entity.Role;
import se.lexicon.mvcthymeleaf.model.entity.User;
import se.lexicon.mvcthymeleaf.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found Exception."));
        // convert User entity to UserDetails of Spring Security object through the UserBuilder class
        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .credentialsExpired(user.isExpired())
                .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new));

        return userBuilder.build();
    }

}
