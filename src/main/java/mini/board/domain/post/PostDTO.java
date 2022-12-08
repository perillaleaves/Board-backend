package mini.board.domain.post;

import mini.board.domain.user.UserDTO;

import java.time.LocalDateTime;

public class PostDTO {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDTO userDTO;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public UserDTO getUserDTO() {
        return userDTO;
    }

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, UserDTO userDTO) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userDTO = userDTO;
    }
}
