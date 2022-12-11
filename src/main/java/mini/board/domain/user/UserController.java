package mini.board.domain.user;

import mini.board.exception.APIError;
import mini.board.response.ApiResponse;
import mini.board.response.ErrorResponse;
import mini.board.response.Response;
import mini.board.response.ValidateResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 4. 유저 아이디 중복 확인
    @GetMapping("/overlap/loginid")
    public Response<ApiResponse> overlapLoginId(@ModelAttribute User user) {

        try {
            userService.findByLoginId(user.getLoginId());
            return new Response<>(new ValidateResponse("availableId", "사용 가능한 아이디 입니다."));
        }  catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 5. 유저 연락처 중복 확인
    @GetMapping("/overlap/phonenumber")
    public Response<ApiResponse> overlapPhoneNum(@ModelAttribute User user) {

        try {
            userService.findByPhoneNum(user.getPhoneNum());
            return new Response<>(new ValidateResponse("availablePhoneNum", "사용 가능한 연락처 입니다."));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 6. 유저 이메일 중복 확인
    @GetMapping("/overlap/email")
    public Response<ApiResponse> overlapEmail(@ModelAttribute User user) {

        try {
            userService.findByEmail(user.getEmail());
            return new Response<>(new ValidateResponse("availableEmail", "사용 가능한 이메일 입니다."));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }
    }

    // 7. 로그인한 유저 정보 조회
    @GetMapping("/user")
    public Response<ApiResponse> userProfile(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        return new Response<>(new ApiResponse(loggedUser));
    }

    // 8. 로그인한 유저 정보 수정
    @PutMapping("/user")
    public Response<ApiResponse> userProfileUpdate(@RequestBody User user, HttpServletRequest request) {

        try {
            User updateUser = userService.update(user, request);
            return new Response<>(new ApiResponse(updateUser));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

}
