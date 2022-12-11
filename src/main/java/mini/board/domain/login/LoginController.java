package mini.board.domain.login;

import mini.board.response.ErrorResponse;
import mini.board.response.Response;
import mini.board.response.ApiResponse;
import mini.board.domain.user.User;
import mini.board.domain.user.UserService;
import mini.board.exception.APIError;
import mini.board.response.ValidateResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
    public Response<ApiResponse> signup(@RequestBody User user) {

        try {
            User newUser = userService.create(user);
            return new Response<>(new ApiResponse(newUser));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 2. 로그인
    @PostMapping("/login")
    // FIXME: User -> DTO 로
    public Response<ApiResponse> login(@RequestBody User user, HttpServletRequest request) {

        try {
            User loggedUser = loginService.signIn(user.getLoginId(), user.getPassword());
            HttpSession session = request.getSession();
            session.setAttribute("loggedUser", loggedUser);
            session.setAttribute("user_id", loggedUser.getId());

            return new Response<>(new ApiResponse(loggedUser));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 3. 로그아웃
    @PostMapping("/logout")
    public Response<ApiResponse> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            return new Response<>(new ValidateResponse(" logout", "로그아웃"));
        }
        return new Response<>(new ValidateResponse(null, null));

    }

}
