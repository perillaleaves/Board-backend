package mini.board.domain.user;

import java.time.LocalDateTime;

public class UserDTO {

    private Long userId;
    private String loginId;
    private String password;
    private String name;
    private String phoneNum;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getUserId() { return userId; }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserDTO() {
    }

    public UserDTO(String name) {
        this.name = name;
    }

    public UserDTO(Long userId, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


    public UserDTO(Long userId, String loginId, String password, String name, String phoneNum, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
