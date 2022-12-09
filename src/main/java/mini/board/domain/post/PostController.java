package mini.board.domain.post;

import mini.board.exception.APIError;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
            // request param 뺴주세요
            // API(postController)HttpRequest -> postService.create(HttpRequest)
            // Admin API(postController)HttpRequest -> postService.create(HttpRequest)
            // Batch(postController) HttpRequest(X) -> postService.create2()
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
    public Map<String, Object> posts(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Map<String, Object> map = new HashMap<>();

        List<Post> posts = postService.getPosts(pageable);
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = PostDTO.boardList(post);
            postDTOs.add(postDTO);
        }

        map.put("posts", postDTOs);

        return map;
    }

    // 11. 게시물 상세 조회
    @GetMapping("/post/{postId}")
    public Map<String, Object> post(@PathVariable("postId") Long postId) {
        Map<String, Object> map = new HashMap<>();

        Post post = postService.getPost(postId).get();
        PostDTO postDTO = PostDTO.board(post);

        map.put("post", postDTO);

        return map;
    }

    // 12. 게시글 수정
    @PutMapping("/post/{post_id}/update")
    //FIXME: /post/{post_id}/update -> /post/{post_id}
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
