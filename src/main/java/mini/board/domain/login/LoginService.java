package mini.board.domain.login;

import mini.board.domain.user.User;
import mini.board.domain.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> signIn(User user) {
        return Optional.ofNullable(userService.findByLoginId(user)
                .filter(m -> m.getPassword().equals(user.getPassword()))
                .orElse(null));
    }

}
