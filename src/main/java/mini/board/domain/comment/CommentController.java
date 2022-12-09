package mini.board.domain.comment;

import mini.board.exception.APIError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 13. 댓글 작성
    @PostMapping("/post/{post_id}/comment/create")
    public Map<String, Object> create(@PathVariable("post_id") Long postId, @RequestBody Comment comment, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        map.put("success", success);
        map.put("error", error);

        try {
            commentService.create(postId, comment, request);
            success.put("code", "create");
            success.put("message", "댓글 작성");
            error.put("code", "");
            error.put("message", "");
        } catch (APIError e) {
            success.put("code", "");
            success.put("message", "");
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
        }

        return map;
    }

    // 14. 댓글 수정
    @PutMapping("/comment/{comment_id}/update")
    public Map<String, Object> update(@PathVariable("comment_id") Long commentId, @RequestBody Comment comment, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        map.put("success", success);
        map.put("error", error);

        try {
            commentService.update(commentId, comment, request);
            success.put("code", "update");
            success.put("message", "댓글 수정");
            error.put("code", "");
            error.put("message", "");
        } catch (APIError e) {
            success.put("code", "");
            success.put("message", "");
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
        }

        return map;
    }

    // 15. 댓글 삭제
    @DeleteMapping("/comment/{comment_id}/delete")
    public Map<String, Object> delete(@PathVariable("comment_id") Long commentId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> success = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        map.put("success", success);
        map.put("error", error);

        try {
            commentService.delete(commentId, request);
            success.put("code", "delete");
            success.put("message", "댓글 삭제");
            error.put("code", "");
            error.put("message", "");
        } catch (APIError e) {
            success.put("code", "");
            success.put("message", "");
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
        }

        return map;
    }

}
