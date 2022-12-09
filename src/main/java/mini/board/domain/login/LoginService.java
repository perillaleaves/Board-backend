package mini.board.domain.login;

import mini.board.domain.user.User;
import mini.board.domain.user.UserService;
import mini.board.exception.APIError;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private final UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public User signIn(String loginId, String password) {

        User getUser = userService.findByLoginId(loginId).orElse(null);


        if (getUser == null) {
            throw new APIError("EmptyLoginId", "아이디가 존재하지 않습니다.");
        }
        if (!getUser.getPassword().equals(password)) {
            throw new APIError("InconsistencyPassword", "비밀번호가 일치하지 않습니다.");
        }

        return getUser;
    }



}