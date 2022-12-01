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

        try {
            Map<String, Object> success = new HashMap<>();
            Map<String, String> error = new HashMap<>();
            userService.save(user);
            map.put("success", success);
            success.put("code", "signup");
            success.put("message", "회원가입");
            map.put("error", error);
            error.put("code", "");
            error.put("message", "");
            return map;
        } catch (APIError e) {
            Map<String, String> error = new HashMap<>();
            map.put("error", error);
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
            return map;
        }
    }

    // 유저 아이디 중복 확인 버튼
    @GetMapping("/overlap")
    public Map<String, Object> loginIdOverlap(@ModelAttribute User user) {
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

    // 로그인한 유저 정보 조회
    @GetMapping("/user")
    public Map<String, Object> userProfile(HttpServletRequest request, Model model) {
        Map<String, Object> map = new HashMap<>();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        Optional<User> user = userService.findByLoginId(loginUser);
        model.addAttribute("user", user);
        map.put("user", user);

        return map;
    }

    // 로그인한 유저 정보 수정
    @PutMapping("/update")
    public Map<String, Object> userProfileUpdate(HttpServletRequest request, User user) {
        Map<String, Object> map = new HashMap<>();

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        userService.update(loginUser);

        map.put("user", user.getPassword());

        return map;
    }

}
