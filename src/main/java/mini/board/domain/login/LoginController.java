package mini.board.domain.login;

import mini.board.domain.user.User;
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

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    // 로그인
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, Object> fail = new HashMap<>();

        try {
            User loginUser = loginService.signIn(user);
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);
            session.setAttribute("userId", loginUser.getId());

            map.put("data", success);
            success.put("code", "login");
            success.put("message", "로그인");
            map.put("error", fail);
            fail.put("code", "");
            fail.put("message", "");
            return map;
        } catch (APIError e){
            map.put("data", success);
            success.put("code", "");
            success.put("message", "");
            map.put("error", fail);
            fail.put("code", e.getCode());
            fail.put("message", e.getMessage());
            return map;
        }

    }

    // 로그아웃
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            Map<String, Object> success = new HashMap<>();
            map.put("success", success);
            success.put("code", "logout");
            success.put("message", "로그아웃");
        }

        return map;
    }

}
