package fi.haagahelia.mjtea.web;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import fi.haagahelia.mjtea.model.AppUser;
import fi.haagahelia.mjtea.model.AppUserRepository;

// marks this as a service component that Spring manages and uses automatically during login
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final AppUserRepository repository;

    public UserDetailServiceImpl(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    // When someone logs in, Spring calls this method to find the user in the
    // database
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm user trong database theo username
        AppUser curruser = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(username);
        // It hands the hashed password and the role to Spring so it can verify the
        // login
        builder.password(curruser.getPassword());
        builder.roles(curruser.getRole());
        return builder.build();
    }
}