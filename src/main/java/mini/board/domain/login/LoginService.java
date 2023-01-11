package mini.board.domain.login;

import mini.board.domain.user.User;
import mini.board.domain.user.UserRepository;
import mini.board.domain.user.UserService;
import mini.board.exception.APIError;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class LoginService {

    private final UserService userService;
    private final UserRepository userRepository;

    public LoginService(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public User signIn(String loginId, String password) {

        User getUser = userRepository.findByLoginId(loginId).orElse(null);

        if (getUser == null) {
            throw new APIError("InconsistencyPassword", "아이디가 일치하지 않습니다.");
        }
        if (!getUser.getPassword().equals(password)) {
            throw new APIError("InconsistencyPassword", "비밀번호가 일치하지 않습니다.");
        }

        return getUser;
    }


}