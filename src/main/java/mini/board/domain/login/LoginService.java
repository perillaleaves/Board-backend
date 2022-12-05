package mini.board.domain.login;

import mini.board.domain.user.User;
import mini.board.domain.user.UserService;
import mini.board.exception.APIError;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LoginService {

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public User signIn(User user) {

        User getUser = userService.findByLoginId(user).orElse(null);

        if (getUser == null) {
            throw new APIError("EmptyLoginId", "아이디가 존재하지 않습니다.");
        }
        if (!getUser.getPassword().equals(user.getPassword())) {
            throw new APIError("InconsistencyPassword", "비밀번호가 일치하지 않습니다.");
        }

        return getUser;
    }



}