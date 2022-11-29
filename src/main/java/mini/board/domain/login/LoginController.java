package mini.board.domain.login;

import mini.board.domain.user.User;
import mini.board.domain.user.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/signin")
    public Map<String, Object> login(@ModelAttribute UserDTO userDTO, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Optional<User> loginUser = loginService.signIn(new User(userDTO.getLoginId(), userDTO.getPassword()));

        if (loginUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);

            map.put("result", "login");
        } else {
            map.put("result", "fail");
        }

        return map;
    }



}
