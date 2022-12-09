package mini.board.domain.comment;

import mini.board.domain.post.PostDTO;
import mini.board.domain.user.UserDTO;

import java.time.LocalDateTime;

public class CommentDTO {

    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private UserDTO userDTO;
    private PostDTO postDTO;

    public Long getCommentId() {
        return commentId;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public PostDTO getPostDTO() {
        return postDTO;
    }

    public CommentDTO() {
    }

    public CommentDTO(Long commentId, String content, LocalDateTime createdAt, LocalDateTime updatedAt, UserDTO userDTO) {
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userDTO = userDTO;
    }
}
