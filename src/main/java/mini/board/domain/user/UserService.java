package mini.board.domain.user;

import mini.board.exception.APIError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User create(User user) {
        validate(user);

        LocalDateTime date = LocalDateTime.now();
        user.setCreatedAt(date);
        user.setUpdatedAt(date);

        return userRepository.save(user);
    }

    @Transactional
    public Optional<User> findByLoginId(User user) {

        return userRepository.findByLoginId(user.getLoginId());
    }

    @Transactional
    public Optional<User> findByPhoneNum(User user) {

        return userRepository.findByPhoneNum(user.getPhoneNum());
    }

    @Transactional
    public Optional<User> findByEmail(User user) {

        return userRepository.findByEmail(user.getEmail());
    }

    @Transactional
    public User update(User user, HttpServletRequest request) {

        updateValidate(user, request);
        LocalDateTime date = LocalDateTime.now();
        User findUser = userRepository.findById(user.getId()).get();

        findUser.setPassword(user.getPassword());
        findUser.setPhoneNum(user.getPhoneNum());
        findUser.setEmail(user.getEmail());
        findUser.setUpdatedAt(date);

        return userRepository.save(findUser);
    }

    private void validate(User user) {
        boolean email_validate = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", user.getEmail());
        boolean password_validate = Pattern.matches("^(?=.*?[A-Z]+).{8,}", user.getPassword());

        if (user.getName().isBlank()) {
            throw new APIError("InvalidName", "이름을 입력해주세요.");
        }
        if (user.getLoginId().isBlank()) {
            throw new APIError("InvalidId", "아이디를 입력해주세요.");
        }
        if (user.getLoginId().length() < 8) {
            throw new APIError("LengthId", "아이디를 8글자 이상 입력해주세요.");
        }
        if (user.getPassword().isBlank()) {
            throw new APIError("InvalidPassword", "비밀번호를 입력해주세요.");
        }
        if (user.getPassword().length() < 8) {
            throw new APIError("LengthPassword", "비밀번호를 8글자 이상 입력해주세요.");
        }
        if (!password_validate) {
            throw new APIError("FormPassword", "비밀번호를 양식에 맞게 입력해주세요.");
        }
        if (user.getPhoneNum().isBlank()) {
            throw new APIError("InvalidPhoneNumber", "연락처를 입력해주세요.");
        }
        if (user.getEmail().isBlank()) {
            throw new APIError("InvalidEmail", "이메일을 입력해주세요.");
        }
        if (!email_validate) {
            throw new APIError("FormEmail", "이메일을 양식에 맞게 입력해주세요.");
        }

        Optional<User> userByLoginId = userRepository.findByLoginId(user.getLoginId());
        if (userByLoginId.isPresent()) {
            throw new APIError("ExistsId", "이미 존재하는 아이디 입니다.");
        }

        Optional<User> userByPhoneNum = userRepository.findByPhoneNum(user.getPhoneNum());
        if (userByPhoneNum.isPresent()) {
            throw new APIError("ExistsPhoneNumber", "이미 존재하는 연락처 입니다.");
        }

        Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail.isPresent()) {
            throw new APIError("ExistsEmail", "이미 존재하는 이메일 입니다.");
        }
    }

    private void updateValidate(User user, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        boolean email_validate = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", user.getEmail());
        boolean password_validate = Pattern.matches("^(?=.*?[A-Z]+).{8,}", user.getPassword());

        if (user.getPassword().isBlank()) {
            throw new APIError("InvalidPassword", "비밀번호를 입력해주세요.");
        }
        if (user.getPassword().length() < 8) {
            throw new APIError("LengthPassword", "비밀번호를 8글자 이상 입력해주세요.");
        }
        if (!password_validate) {
            throw new APIError("FormPassword", "비밀번호를 양식에 맞게 입력해주세요.");
        }
        if (user.getPhoneNum().isBlank()) {
            throw new APIError("InvalidPhoneNumber", "연락처를 입력해주세요.");
        }
        if (user.getEmail().isBlank()) {
            throw new APIError("InvalidEmail", "이메일을 입력해주세요.");
        }
        if (!email_validate) {
            throw new APIError("FormEmail", "이메일을 양식에 맞게 입력해주세요.");
        }
    }

}
