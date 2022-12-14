package mini.board.domain.user;

import java.time.LocalDateTime;

public class UserSignUpRequest {

    private Long userId;
    private String loginId;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    private LocalDateTime createdAt;

    public Long getUserId() {
        return userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserSignUpRequest(Long userId, String loginId, String name, String phoneNum, String email, LocalDateTime createdAt) {
        this.userId = userId;
        this.loginId = loginId;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.createdAt = createdAt;
    }
}
