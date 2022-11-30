package mini.board.domain.user;

import mini.board.exception.APIError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User save(User user) {
        validate(user);

        LocalDateTime date = LocalDateTime.now();
        user.setCreatedAt(date);
        user.setUpdatedAt(date);

        User saveUser = userRepository.save(user);

        return saveUser;
    }

    @Transactional
    public Optional<User> findByLoginId(User user) {

        return userRepository.findByLoginId(user.getLoginId());
    }

    @Transactional
    public Optional<User> findById(User user) {

        return userRepository.findByLoginId(user.getLoginId());
    }

    @Transactional
    public boolean overlapByPhoneNum(User user) {
        Optional<User> findPhoneNum = userRepository.findByPhoneNum(user.getPhoneNum());
        if (findPhoneNum.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public boolean overlapByEmail(User user) {
        Optional<User> findEmail = userRepository.findByEmail(user.getEmail());
        if (findEmail.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private void validate(User user) {
        Map<String, Object> map = new HashMap<>();
        boolean email_validate = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", user.getEmail());
        boolean password_validate = Pattern.matches("^(?=.*?[A-Z]+).{8,}", user.getPassword());

        if (user.getName().length() < 0) {
            throw new APIError("InvalidName", "이름을 입력해주세요.");
        }
        if (user.getLoginId().length() < 8) {
            throw new APIError("InvalidId", "아이디를 8글자 이상 입력해주세요.");
        }
        if (user.getPassword().length() < 8) {
            throw new APIError("InvalidPassword", "비밀번호를 8글자 이상 입력해주세요.");
        }
        if (!password_validate) {
            throw new APIError("InvalidPassword", "비밀번호를 양식에 맞게 입력해주세요.");
        }
        if (user.getPhoneNum().length() < 0) {
            throw new APIError("InvalidPhoneNumber", "연락처를 입력해주세요.");
        }
        if (user.getEmail().length() < 0) {
            throw new APIError("InvalidEmail", "이메일을 입력해주세요.");
        }
        if (!email_validate) {
            throw new APIError("InvalidEmail", "이메일을 양식에 맞게 입력해주세요.");
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


}
