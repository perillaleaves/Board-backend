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
            // success
            User saveUser = userService.save(user);
            UserDTO userDTO = new UserDTO(saveUser.getLoginId(), saveUser.getName());
            map.put("user", userDTO);
            return map;
        } catch (APIError e) {
            Map<String, String> error = new HashMap<>();
            map.put("error", error);
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
            return map;
        }
    }

    // 로그인한 유저 정보 조회
//    @GetMapping("/user")
//    public Map<String, Object> userProfile(HttpServletRequest request, Model model) {
//        Map<String, Object> map = new HashMap<>();
//
//        HttpSession session = request.getSession();
//        int userId = (Integer) session.getAttribute("id");
//
//        Optional<User> user = userService.findById(new User());
//        model.addAttribute("user", user);
//        map.put("user", user);
//
//        return map;
//    }

    // 유저 아이디 중복 확인 버튼
    @GetMapping("/overlap")
    public Map<String, Object> loginIdOverlap(@ModelAttribute User user) {
        Map<String, Object> map = new HashMap<>();
        if (userService.findByLoginId(user).isPresent()) {
            map.put("code", "overlap");
            map.put("message", "이미 사용중인 아이디 입니다.");
        } else {
            map.put("code", "available");
            map.put("message", "사용 가능한 아이디 입니다.");
        }

        return map;
    }

    // 회원가입 시 유저 연락처 & 이메일 중복 확인
//    @GetMapping("/overlap")
//    public Map<String, Object> phoneNumEmailOverlap(@RequestBody User user) {
//        Map<String, Object> map = new HashMap<>();
//        if (userService.overlapByPhoneNum(user)) {
//            map.put("overlap", true);
//        }
//        if (userService.overlapByEmail(user)) {√
//            map.put("overlap", true);
//        }
//        map.put("overlap", false);
//
//        return map;
//    }


}
