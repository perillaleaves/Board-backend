package mini.board.domain.post;

import mini.board.domain.comment.CommentDTO;
import mini.board.domain.user.UserDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long commentSize;

    private UserDTO userDTO;

    private List<CommentDTO> comments = new ArrayList<>();

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

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public Long getCommentSize() { return commentSize; }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public PostDTO() {
    }

    public PostDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long commentSize, UserDTO userDTO) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentSize = commentSize;
        this.userDTO = userDTO;
    }

    public PostDTO(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt, Long commentSize, UserDTO userDTO, List<CommentDTO> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.commentSize = commentSize;
        this.userDTO = userDTO;
        this.comments = comments;
    }

    public static PostDTO boardList(Post post) {
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt(), post.getCommentSize(),
                new UserDTO(post.getUser().getId(), post.getUser().getName(), post.getUser().getCreatedAt(), post.getUser().getUpdatedAt()));
    }


    public static PostDTO board(Post post) {
        PostDTO postDto = new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(),post.getUpdatedAt(), post.getCommentSize(),
        new UserDTO(post.getUser().getId(), post.getUser().getName(), post.getUser().getCreatedAt(), post.getUser().getUpdatedAt()),
                post.getComments().stream().map(comment ->
                        new CommentDTO(comment.getId(), comment.getContent(), comment.getCreatedAt(), comment.getUpdatedAt(),
                                new UserDTO(comment.getUser().getName()))).toList());

        return postDto;
    }

}
