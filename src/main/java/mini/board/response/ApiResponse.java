package mini.board.response;

import mini.board.domain.comment.Comment;
import mini.board.domain.comment.CommentDTO;
import mini.board.domain.post.Post;
import mini.board.domain.post.PostDTO;
import mini.board.domain.user.User;
import mini.board.domain.user.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse {

    private User user;

    private PostDTO postDTO;

    private List<PostDTO> postDTOs = new ArrayList<>();

    private CommentDTO commentDTO;

    public User getUser() {
        return user;
    }


    public PostDTO getPostDTO() {
        return postDTO;
    }

    public List<PostDTO> getPostDTOs() {
        return postDTOs;
    }

    public CommentDTO getCommentDTO() {
        return commentDTO;
    }

    public ApiResponse(User user) { this.user = user; }

    public ApiResponse(PostDTO postDTO) {
        this.postDTO = postDTO;
    }

    public ApiResponse(List<PostDTO> postDTOs) {
        this.postDTOs = postDTOs;
    }

    public ApiResponse(CommentDTO commentDTO) {
        this.commentDTO = commentDTO;
    }
}
