package mini.board.domain.user;

import mini.board.exception.APIError;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, String> error = new HashMap<>();

        try {
            userService.create(user);
            map.put("success", success);
            success.put("code", "signup");
            success.put("message", "회원가입");
            map.put("error", error);
            error.put("code", "");
            error.put("message", "");
            return map;
        } catch (APIError e) {
            map.put("success", success);
            success.put("code", "");
            success.put("message", "");
            map.put("error", error);
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
            return map;
        }
    }

    // 유저 아이디 중복 확인
    @GetMapping("/overlap/loginId")
    public Map<String, Object> overlapLoginId(@ModelAttribute User user) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> validate = new HashMap<>();
        if (userService.findByLoginId(user).isPresent()) {
            map.put("validate", validate);
            validate.put("code", "overlap");
            validate.put("message", "이미 사용중인 아이디 입니다.");
        } else {
            map.put("validate", validate);
            validate.put("code", "available");
            validate.put("message", "사용 가능한 아이디 입니다.");
        }

        return map;
    }

    // 유저 연락처 중복 확인
    @GetMapping("/overlap/phoneNum")
    public Map<String, Object> overlapPhoneNum(@ModelAttribute User user) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> validate = new HashMap<>();
        if (userService.findByPhoneNum(user).isPresent()) {
            map.put("validate", validate);
            validate.put("code", "overlap");
            validate.put("message", "이미 사용중인 연락처 입니다.");
        } else {
            map.put("validate", validate);
            validate.put("code", "available");
            validate.put("message", "사용 가능한 연락처 입니다.");
        }

        return map;
    }

    // 유저 이메일 중복 확인
    @GetMapping("/overlap/email")
    public Map<String, Object> overlapEmail(@ModelAttribute User user) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> validate = new HashMap<>();
        if (userService.findByEmail(user).isPresent()) {
            map.put("validate", validate);
            validate.put("code", "overlap");
            validate.put("message", "이미 사용중인 이메일 입니다.");
        } else {
            map.put("validate", validate);
            validate.put("code", "available");
            validate.put("message", "사용 가능한 이메일 입니다.");
        }

        return map;
    }

    // 로그인한 유저 정보 조회
    @GetMapping("/user")
    public Map<String, Object> userProfile(HttpServletRequest request, Model model) {
        Map<String, Object> map = new HashMap<>();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        User user = userService.findByLoginId(loginUser).get();
        UserDTO userDTO = new UserDTO(user.getId(), user.getLoginId(), user.getPassword(), user.getName(), user.getPhoneNum(), user.getEmail(), user.getCreatedAt(), user.getUpdatedAt());
        model.addAttribute("user", userDTO);
        map.put("user", user);

        return map;
    }

    // 로그인한 유저 정보 수정
    @PutMapping("/user/update")
    public Map<String, Object> userProfileUpdate(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, String> error = new HashMap<>();
        HttpSession session = request.getSession();
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
