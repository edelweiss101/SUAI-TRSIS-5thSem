package info.stepanoff.trsis.samples.service.login;

import info.stepanoff.trsis.samples.db.model.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class MyLogin extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = 4L;
    private User user;

    public MyLogin(User user) {
        super(user.getLogin(), user.getPassHash(), AuthorityUtils.createAuthorityList("ALL"));
        this.user = user;
    }
}
