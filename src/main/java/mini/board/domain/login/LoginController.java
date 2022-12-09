package mini.board.domain.login;

import mini.board.domain.user.User;
import mini.board.domain.user.UserService;
import mini.board.exception.APIError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;

    public LoginController(LoginService loginService, UserService userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    // 1. 회원가입
    @PostMapping("/signup")
    public Map<String, Object> signup(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, String> error = new HashMap<>();
        map.put("success", success);
        map.put("error", error);

        try {
            userService.create(user);
            success.put("code", "signup");
            success.put("message", "회원가입");
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

    // 2. 로그인
    @PostMapping("/login")
    // FIXME: User -> DTO 로
    public Map<String, Object> login(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, Object> fail = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        map.put("success", success);
        map.put("fail", fail);
        map.put("data", data);

        try {
            User loginUser = loginService.signIn(user.getLoginId(), user.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);
            session.setAttribute("userId", loginUser.getId());

            success.put("code", "login");
            success.put("message", "로그인");
            fail.put("code", "");
            fail.put("message", "");
            data.put("data", loginUser);
        } catch (APIError e){
            success.put("code", "");
            success.put("message", "");
            fail.put("code", e.getCode());
            fail.put("message", e.getMessage());
        }

        return map;
    }

    // 3. 로그아웃
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        map.put("success", success);

        if (session != null) {
            session.invalidate();
            success.put("code", "logout");
            success.put("message", "로그아웃");
        }

        return map;
    }

}
