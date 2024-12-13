package info.stepanoff.trsis.samples.service.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoggedUserDetailsService implements UserDetailsService {
    private final LoginService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        System.out.println("[LoggedUserDetailsService] login: <" + login + ">");

        UserDetails details = userService.loadUserByUsername(login);
        if (details != null) {
            System.out.println("[LoggedUserDetailsService] UserDetails.username: <" + details.getUsername() + ">");
            System.out.println("[LoggedUserDetailsService] UserDetails.password: <" + details.getPassword() + ">");

            return details;
        } else {
            throw new UsernameNotFoundException("Invalid user " + login);
        }
    }
}
