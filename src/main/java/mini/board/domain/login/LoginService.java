package mini.board.domain.login;

import mini.board.domain.user.User;
import mini.board.domain.user.UserService;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public User signIn(User user) {
        return userService.findByLoginId(user)
                .filter(m -> m.getPassword().equals(user.getPassword()))
                .orElse(null);
    }

}