package mini.board.domain.user;

import mini.board.exception.APIError;
import mini.board.response.ErrorResponse;
import mini.board.response.Response;
import mini.board.response.ValidateResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    // 4. 유저 아이디 중복 확인
    @GetMapping("/overlap/loginid")
    public Response<ValidateResponse> overlapLoginId(@ModelAttribute UserOverlapLoginRequest request) {

        try {
            request.validate();
            User user = userService.findByLoginIdOrNull(request.getLoginId());
            if (user != null) {
                throw new APIError("ExistsId", "이미 존재하는 아이디 입니다.");
            }
            return new Response<>(new ValidateResponse("availableId", "사용 가능한 아이디 입니다."));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 5. 유저 연락처 중복 확인
    @GetMapping("/overlap/phonenumber")
    public Response<ValidateResponse> overlapPhoneNum(@ModelAttribute User user) {

        try {
            userService.findByPhoneNum(user.getPhoneNum());
            return new Response<>(new ValidateResponse("availablePhoneNum", "사용 가능한 연락처 입니다."));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 6. 유저 이메일 중복 확인
    @GetMapping("/overlap/email")
    public Response<ValidateResponse> overlapEmail(@ModelAttribute User user) {

        try {
            userService.findByEmail(user.getEmail());
            return new Response<>(new ValidateResponse("availableEmail", "사용 가능한 이메일 입니다."));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }
    }

    // 7. 로그인한 유저 정보 조회
    @GetMapping("/user/{user_id}")
    public Response<UserLoginRequest> userProfile(@PathVariable("user_id") Long user_id, HttpServletRequest request) {

//        HttpSession session = request.getSession();
//        User loggedUser = (User) session.getAttribute("loggedUser");
        User loggedUser = userRepository.findById(user_id).orElse(null);

        UserLoginRequest user = new UserLoginRequest(loggedUser.getId(), loggedUser.getLoginId(), loggedUser.getName(), loggedUser.getPhoneNum(), loggedUser.getEmail(), loggedUser.getCreatedAt(), loggedUser.getUpdatedAt());

        return new Response<>(user);
    }

    // 8. 로그인한 유저 정보 수정
    @PutMapping("/user/{user_id}")
    public Response<UserDTO> userProfileUpdate(@PathVariable("user_id") Long user_id, @RequestBody UserDTO userDTO, HttpServletRequest request) {

        try {
            User updateUser = userService.update(user_id, userDTO, request);
            UserDTO user = new UserDTO(updateUser.getId(), updateUser.getLoginId(), updateUser.getPassword(), updateUser.getName(), updateUser.getPhoneNum(), updateUser.getEmail(), updateUser.getCreatedAt(), updateUser.getUpdatedAt());

            return new Response<>(user);
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

}
