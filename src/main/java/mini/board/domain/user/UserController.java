package mini.board.domain.user;

import mini.board.exception.APIError;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 4. 유저 아이디 중복 확인
    @GetMapping("/overlap/loginId")
    public Map<String, Object> overlapLoginId(@ModelAttribute User user) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> validate = new HashMap<>();
        map.put("validate", validate);

        if (userService.findByLoginId(user.getLoginId()).isPresent()) {
            validate.put("code", "overlap");
            validate.put("message", "이미 사용중인 아이디 입니다.");
        } else {
            validate.put("code", "available");
            validate.put("message", "사용 가능한 아이디 입니다.");
        }

        return map;
    }

    // 5. 유저 연락처 중복 확인
    @GetMapping("/overlap/phonenumber")
    public Map<String, Object> overlapPhoneNum(@ModelAttribute User user) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> validate = new HashMap<>();
        map.put("validate", validate);

        if (userService.findByPhoneNum(user.getPhoneNum()).isPresent()) {
            validate.put("code", "overlap");
            validate.put("message", "이미 사용중인 연락처 입니다.");
        } else {
            validate.put("code", "available");
            validate.put("message", "사용 가능한 연락처 입니다.");
        }

        return map;
    }

    // 6. 유저 이메일 중복 확인
    @GetMapping("/overlap/email")
    public Map<String, Object> overlapEmail(@ModelAttribute User user) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> validate = new HashMap<>();
        map.put("validate", validate);

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            validate.put("code", "overlap");
            validate.put("message", "이미 사용중인 이메일 입니다.");
        } else {
            validate.put("code", "available");
            validate.put("message", "사용 가능한 이메일 입니다.");
        }

        return map;
    }

    // 7. 로그인한 유저 정보 조회
    @GetMapping("/user")
    public Map<String, Object> userProfile(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        User user = userService.findByLoginId(loginUser.getLoginId()).get();
        UserDTO userDTO = new UserDTO(user.getId(), user.getLoginId(), user.getPassword(), user.getName(), user.getPhoneNum(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
        map.put("user", userDTO);

        return map;
    }

    // 8. 로그인한 유저 정보 수정
    @PutMapping("/user/update")
    public Map<String, Object> userProfileUpdate(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, String> error = new HashMap<>();
        map.put("success", success);
        map.put("error", error);

        try {
            userService.update(user, request);
            success.put("code", "update");
            success.put("message", "유저 정보 수정");
            error.put("code", "");
            error.put("message", "");
        } catch (APIError e) {
            success.put("code", "");
            success.put("message", "");
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
        }

        return map;
    }

}
