package mini.board.domain.post;

import mini.board.domain.user.UserDTO;
import mini.board.exception.APIError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 9. 게시글 작성
    @PostMapping("/post/create")
    public Map<String, Object> create(@RequestBody Post post, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        map.put("data", data);
        map.put("error", error);

        try {
            postService.create(post, request);
            data.put("code", "create");
            data.put("message", "게시글 작성");
            error.put("code", "");
            error.put("message", "");
        } catch (APIError e) {
            data.put("code", "");
            data.put("message", "");
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
        }

        return map;
    }

    // 10. 게시글 리스트 조회
    @GetMapping("/posts")
    public Map<String, Object> posts() {
        Map<String, Object> map = new HashMap<>();
        List<Post> posts = postService.getPosts();
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt(),
                    new UserDTO(post.getUser().getId(), post.getUser().getLoginId(), post.getUser().getPassword(), post.getUser().getName(), post.getUser().getPhoneNum(), post.getUser().getEmail(), post.getUser().getCreatedAt(), post.getUser().getUpdatedAt()));
            postDTOs.add(postDTO);
        }

        map.put("posts", postDTOs);

        return map;
    }

    // 11. 게시물 상세 조회
    @GetMapping("/post/{postId}")
    public Map<String, Object> post(@PathVariable("postId") Post post) {
        Map<String, Object> map = new HashMap<>();

        Post getPost = postService.getPost(post).orElse(null);
        PostDTO postDTO = new PostDTO(getPost.getId(), getPost.getTitle(), getPost.getContent(), getPost.getCreatedAt(), getPost.getUpdatedAt(),
                new UserDTO(getPost.getUser().getId(), getPost.getUser().getLoginId(), getPost.getUser().getPassword(), getPost.getUser().getName(), getPost.getUser().getPhoneNum(), getPost.getUser().getEmail(), getPost.getUser().getCreatedAt(), getPost.getUser().getUpdatedAt()));

        map.put("post", postDTO);
        return map;
    }

    // 12. 게시글 수정
    @PutMapping("/post/{post_id}/update")
    public Map<String, Object> update(@PathVariable("post_id") Long postId, @RequestBody Post post, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        map.put("data", data);
        map.put("error", error);

        try {
            postService.updatePost(postId, post, request);
            data.put("code", "update");
            data.put("message", "게시글 수정");
            error.put("code", "");
            error.put("message", "");
        } catch (APIError e) {
            data.put("code", "");
            data.put("message", "");
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
        }

        return map;
    }

    // 16. 게시글 삭제
    @DeleteMapping("/post/{post_id}/delete")
    public Map<String, Object> delete(@PathVariable("post_id") Long postId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        map.put("data", data);
        map.put("error", error);

        try {
            postService.delete(postId, request);
            data.put("code", "delete");
            data.put("message", "게시글 삭제");
            error.put("code", "");
            error.put("message", "");
        } catch (APIError e) {
            data.put("code", "");
            data.put("message", "");
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
        }

        return map;
    }

}
