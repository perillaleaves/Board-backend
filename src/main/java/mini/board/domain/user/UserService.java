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

        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    private void validate(User user) {
        // 0.0001ms
        if (user.getLoginId().length() < 8) {
            throw new Error("Id length must be greater than 8");
        }
        if (user.getPassword().length() > 8) {
            throw new Error("Password length must be less than 8");
        }
        if (user.getName().length() > 3) {
            throw new Error("Name length must be less than 3");
        }
        if (user.getPhoneNum().length() > 11 || user.getPhoneNum().length() < 12) {
            throw new Error("Phone number length must be 11");
        }
        if (user.getEmail().length() < 20) {
            throw new Error("Email length must be greater than 20");
        }

        // 100ms
        Optional<User> userByLoginId = userRepository.findByLoginId(user.getLoginId());
        if (userByLoginId.isPresent()) {
            throw new Error("이미 존재하는 아이디 입니다.");
        }
    }


}
