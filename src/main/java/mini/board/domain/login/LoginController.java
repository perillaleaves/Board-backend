package mini.board.domain.login;

import mini.board.domain.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public Map<String, Object> signIn(@ModelAttribute LoginDTO loginDTO, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        User loginUser = loginService.signIn(new User(loginDTO.getLoginId(), loginDTO.getPassword()));

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);

        map.put("loginUser", loginUser);
        return map;
    }

}
