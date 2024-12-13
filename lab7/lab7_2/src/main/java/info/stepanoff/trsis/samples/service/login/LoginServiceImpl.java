package info.stepanoff.trsis.samples.service.login;

import info.stepanoff.trsis.samples.db.dao.UserRepository;
import info.stepanoff.trsis.samples.db.model.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        System.out.println("[LoginServiceImpl] login: <" + string + ">");

        User login = userRepository.findByLogin(string);
        if (login == null) {
            throw new UsernameNotFoundException("User not found");
        }

        System.out.println("[LoginServiceImpl] User.id: <" + login.getId() + ">");
        System.out.println("[LoginServiceImpl] User.login: <" + login.getLogin() + ">");
        System.out.println("[LoginServiceImpl] User.passHash: <" + login.getPassHash() + ">");

        return new MyLogin(login);
    }
}
