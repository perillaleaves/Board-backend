package mini.board.domain.post;

import mini.board.domain.user.User;
import mini.board.domain.user.UserDTO;
import mini.board.exception.APIError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성
    @PostMapping("/create")
    public Map<String, Object> create(@RequestBody Post post, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> error = new HashMap<>();
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        try {
            postService.create(post, loginUser);
            map.put("data", data);
            data.put("code", "create");
            data.put("message", "게시글 작성");
            map.put("error", error);
            error.put("code", "");
            error.put("message", "");
            return map;
        } catch (APIError e) {
            map.put("data", data);
            data.put("code", "");
            data.put("message", "");
            map.put("error", error);
            error.put("code", e.getCode());
            error.put("message", e.getMessage());
            return map;
        }
    }

    // 게시글 리스트 조회
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

    // 게시물 상세보기
    @GetMapping("/post/{postId}")
    public Map<String, Object> post(@PathVariable("postId") Post post) {
        Map<String, Object> map = new HashMap<>();

        Post getPost = postService.getPost(post).get();
        PostDTO postDTO = new PostDTO(getPost.getId(), getPost.getTitle(), getPost.getContent(), getPost.getCreatedAt(), getPost.getUpdatedAt(),
                new UserDTO(getPost.getUser().getId(), getPost.getUser().getLoginId(), getPost.getUser().getPassword(), getPost.getUser().getName(), getPost.getUser().getPhoneNum(), getPost.getUser().getEmail(), getPost.getUser().getCreatedAt(), getPost.getUser().getUpdatedAt()));

        map.put("post", postDTO);
        return map;
    }

}
