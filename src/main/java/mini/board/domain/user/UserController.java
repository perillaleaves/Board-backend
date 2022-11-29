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

    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();

        User saveUser = userService.save(user);
        UserDTO userDTO = new UserDTO(saveUser.getLoginId(), saveUser.getName());

        map.put("user", userDTO);

        return map;
    }

    @GetMapping("/")
    public Model userProfile(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        int userId = (Integer) session.getAttribute("userId");

        Optional<User> user = userService.findById(userId);

        model.addAttribute("user", user);

        return model;
    }
}
