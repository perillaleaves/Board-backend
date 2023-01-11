package mini.board.domain.comment;

import mini.board.domain.user.UserDTO;
import mini.board.exception.APIError;
import mini.board.response.ErrorResponse;
import mini.board.response.Response;
import mini.board.response.ValidateResponse;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 13. 댓글 작성
    @PostMapping("/post/{post_id}/comment")
    public Response<CommentDTO> create(@PathVariable("post_id") Long postId, @RequestBody Comment comment, HttpServletRequest request) {

        try {
            Comment addComment = commentService.create(postId, comment, request);
            CommentDTO commentDTO = new CommentDTO(addComment.getId(), addComment.getContent(), addComment.getCreatedAt(), addComment.getUpdatedAt(),
                    new UserDTO(addComment.getUser().getId(), addComment.getUser().getName(), addComment.getUser().getCreatedAt(), addComment.getUser().getUpdatedAt()));
            return new Response<>(commentDTO);
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 14. 댓글 수정
    @PutMapping("/comment/{comment_id}")
    public Response<CommentDTO> update(@PathVariable("comment_id") Long commentId, @RequestBody Comment comment, HttpServletRequest request) {

        try {
            Comment updatedComment = commentService.update(commentId, comment, request);
            CommentDTO commentDTO = new CommentDTO(updatedComment.getId(), updatedComment.getContent(), updatedComment.getCreatedAt(), updatedComment.getUpdatedAt(),
                    new UserDTO(updatedComment.getUser().getId(), updatedComment.getUser().getName(), updatedComment.getUser().getCreatedAt(), updatedComment.getUser().getUpdatedAt()));
            return new Response<>(commentDTO);
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 15. 댓글 삭제
    @DeleteMapping("/comment/{comment_id}")
    public Response<ValidateResponse> delete(@PathVariable("comment_id") Long commentId, HttpServletRequest request) {

        try {
            commentService.delete(commentId, request);
            return new Response<>(new ValidateResponse("delete", "게시글 삭제"));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

}
