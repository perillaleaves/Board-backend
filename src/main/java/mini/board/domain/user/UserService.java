package mini.board.domain.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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

    private void validate(User user) {
        if (user.getLoginId().length() < 8) {
            throw new Error("아이디를 8글자 이상 입력해주세요.");
        }
        if (user.getPassword().length() < 8) {
            throw new Error("비밀번호를 8글자 이상 입력해주세요.");
        }
        if (user.getName().length() < 0) {
            throw new Error("이름을 입력해주세요.");
        }
        if (user.getPhoneNum().length() < 0) {
            throw new Error("연락처를 입력해주세요.");
        }
        if (user.getEmail().length() < 0) {
            throw new Error("이메일을 입력해주세요.");
        }

        Optional<User> userByLoginId = userRepository.findByLoginId(user.getLoginId());
        if (userByLoginId.isPresent()) {
            throw new Error("이미 존재하는 아이디입니다.");
        }
    }


}
