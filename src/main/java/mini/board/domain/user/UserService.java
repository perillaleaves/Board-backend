package mini.board.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User save(User user) {
        validate(user);

        LocalDateTime date = LocalDateTime.now();
        user.setCreatedAt(date);

        User saveUser = userRepository.save(user);

        return saveUser;
    }

    @Transactional
    public Optional<User> findByLoginId(User user) {

        return userRepository.findAll().stream()
                .filter(u -> u.getLoginId().equals(user.getLoginId()))
                .findFirst();
    }

    @Transactional
    public Optional<User> findById(int userId) {

        return userRepository.findAll().stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst();
    }

    private void validate(User user) {
        boolean phone_validate = Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", user.getPhoneNum());
        boolean email_validate = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", user.getEmail());
        boolean password_validate = Pattern.matches("^(?=.*?[A-Z])(?=.*?[0-9]).{8,}", user.getPassword());

        if (user.getLoginId().length() < 8) {
            throw new Error("아이디를 8글자 이상 입력해주세요.");
        }
        if (!password_validate) {
            throw new Error("비밀번호를 양식에 맞게 입력해주세요.");
        }
        if (user.getName().length() < 0) {
            throw new Error("이름을 입력해주세요.");
        }
        if (user.getPhoneNum().length() < 0) {
            throw new Error("연락처를 입력해주세요.");
        }
        if (!phone_validate) {
            throw new Error("연락처를 양식에 맞게 입력해주세요.");
        }
        if (user.getEmail().length() < 0 && email_validate) {
            throw new Error("이메일을 입력해주세요.");
        }
        if (!email_validate) {
            throw new Error("이메일을 양식에 맞게 입력해주세요.");
        }

        Optional<User> userByLoginId = userRepository.findByLoginId(user.getLoginId());
        if (userByLoginId.isPresent()) {
            throw new Error("이미 존재하는 아이디입니다.");
        }
    }


}
