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
    public User create(UserDTO userDTO) {
        validate(userDTO);

        LocalDateTime date = LocalDateTime.now();
        User user = new User(userDTO.getUserId(), userDTO.getLoginId(), userDTO.getPassword(), userDTO.getName(), userDTO.getPhoneNum(), userDTO.getEmail(), userDTO.getCreatedAt(), userDTO.getUpdatedAt());
        user.setCreatedAt(date);
        user.setUpdatedAt(date);

        return userRepository.save(user);
    }

    @Transactional
    public User findByLoginId(String loginId) {

        if (loginId.length() == 0) {
            throw new APIError("InvalidId", "아이디를 입력해주세요.");
        }
        if (loginId.length() < 8) {
            throw new APIError("LengthId", "아이디를 8글자 이상 입력해주세요.");
        }
        if (userRepository.findByLoginId(loginId).isPresent()) {
            throw new APIError("ExistsId", "이미 존재하는 아이디 입니다.");
        }

        return userRepository.findByLoginId(loginId).orElse(null);

    }

    @Transactional
    public User findByPhoneNum(String phoneNum) {

        if (phoneNum.length() == 0) {
            throw new APIError("InvalidPhoneNumber", "연락처를 입력해주세요.");
        }
        if (userRepository.findByPhoneNum(phoneNum).isPresent()) {
            throw new APIError("ExistsPhoneNumber", "이미 존재하는 연락처 입니다.");
        }

        return userRepository.findByPhoneNum(phoneNum).orElse(null);
    }

    @Transactional
    public User findByEmail(String email) {
        boolean email_validate = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", email);

        if (email.length() == 0) {
            throw new APIError("InvalidEmail", "이메일을 입력해주세요.");
        }
        if (!email_validate) {
            throw new APIError("FormEmail", "이메일을 양식에 맞게 입력해주세요.");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new APIError("ExistsEmail", "이미 존재하는 이메일 입니다.");
        }

        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public User update(UserDTO userDTO, HttpServletRequest request) {

        updateValidate(userDTO, request);
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        LocalDateTime date = LocalDateTime.now();
        loggedUser.setPassword(userDTO.getPassword());
        loggedUser.setPhoneNum(userDTO.getPhoneNum());
        loggedUser.setEmail(userDTO.getEmail());
        loggedUser.setUpdatedAt(date);

        return userRepository.save(loggedUser);
    }

    private void validate(UserDTO userDTO) {
        boolean password_validate = Pattern.matches("^(?=.*?[A-Z]+).{8,}", userDTO.getPassword());
        boolean email_validate = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", userDTO.getEmail());

        if (userDTO.getName().isBlank()) {
            throw new APIError("InvalidName", "이름을 입력해주세요.");
        }
        if (userDTO.getLoginId().isBlank()) {
            throw new APIError("InvalidId", "아이디를 입력해주세요.");
        }
        if (userDTO.getLoginId().length() < 8) {
            throw new APIError("LengthId", "아이디를 8글자 이상 입력해주세요.");
        }
        if (userDTO.getPassword().isBlank()) {
            throw new APIError("InvalidPassword", "비밀번호를 입력해주세요.");
        }
        if (userDTO.getPassword().length() < 8) {
            throw new APIError("LengthPassword", "비밀번호를 8글자 이상 입력해주세요.");
        }
        if (!password_validate) {
            throw new APIError("FormPassword", "비밀번호를 양식에 맞게 입력해주세요.");
        }
        if (userDTO.getPhoneNum().isBlank()) {
            throw new APIError("InvalidPhoneNumber", "연락처를 입력해주세요.");
        }
        if (userDTO.getEmail().isBlank()) {
            throw new APIError("InvalidEmail", "이메일을 입력해주세요.");
        }
        if (!email_validate) {
            throw new APIError("FormEmail", "이메일을 양식에 맞게 입력해주세요.");
        }

        Optional<User> userByLoginId = userRepository.findByLoginId(userDTO.getLoginId());
        if (userByLoginId.isPresent()) {
            throw new APIError("ExistsId", "이미 존재하는 아이디 입니다.");
        }

        Optional<User> userByPhoneNum = userRepository.findByPhoneNum(userDTO.getPhoneNum());
        if (userByPhoneNum.isPresent()) {
            throw new APIError("ExistsPhoneNumber", "이미 존재하는 연락처 입니다.");
        }

        Optional<User> userByEmail = userRepository.findByEmail(userDTO.getEmail());
        if (userByEmail.isPresent()) {
            throw new APIError("ExistsEmail", "이미 존재하는 이메일 입니다.");
        }
    }

    private void updateValidate(UserDTO userDTO, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        boolean email_validate = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?", userDTO.getEmail());
        boolean password_validate = Pattern.matches("^(?=.*?[A-Z]+).{8,}", userDTO.getPassword());

        if (userDTO.getPassword().isBlank()) {
            throw new APIError("InvalidPassword", "비밀번호를 입력해주세요.");
        }
        if (userDTO.getPassword().length() < 8) {
            throw new APIError("LengthPassword", "비밀번호를 8글자 이상 입력해주세요.");
        }
        if (!password_validate) {
            throw new APIError("FormPassword", "비밀번호를 양식에 맞게 입력해주세요.");
        }
        if (userDTO.getPhoneNum().isBlank()) {
            throw new APIError("InvalidPhoneNumber", "연락처를 입력해주세요.");
        }
        if (userDTO.getEmail().isBlank()) {
            throw new APIError("InvalidEmail", "이메일을 입력해주세요.");
        }
        if (!email_validate) {
            throw new APIError("FormEmail", "이메일을 양식에 맞게 입력해주세요.");
        }

//        if (userRepository.findByLoginId(user.getLoginId()).isPresent()) {
//            throw new APIError("ExistsId", "이미 존재하는 아이디 입니다.");
//        }
//
//        if (userRepository.findByPhoneNum(user.getPhoneNum()).isPresent()) {
//            throw new APIError("ExistsPhoneNumber", "이미 존재하는 연락처 입니다.");
//        }
//
//        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new APIError("ExistsEmail", "이미 존재하는 이메일 입니다.");
//        }
    }

}
