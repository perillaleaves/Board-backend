package mini.board.domain.user;

import mini.board.domain.post.Post;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String password;

    private String name;

    private String phoneNum;

    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String loginId, String password, String name, String phoneNum, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public User(Long id, String loginId, String password, String name, String phoneNum, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.phoneNum = phoneNum;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
