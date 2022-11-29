package mini.board.domain.user;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3001")
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

        User saveUser = userService.save(user);
        UserDTO userDTO = new UserDTO(saveUser.getLoginId(), saveUser.getName());

        map.put("user", userDTO);

        return map;
    }

    // 로그인한 유저 정보 조회
    @GetMapping("/user")
    public Map<String, Object> userProfile(HttpServletRequest request, Model model) {
        Map<String, Object> map = new HashMap<>();

        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("id");

        Optional<User> user = userService.findById(userId);
        model.addAttribute("user", user);
        map.put("user", user);

        return map;
    }
}
