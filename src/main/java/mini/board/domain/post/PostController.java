package mini.board.domain.post;

import mini.board.domain.user.UserDTO;
import mini.board.exception.APIError;
import mini.board.response.ErrorResponse;
import mini.board.response.Response;
import mini.board.response.ValidateResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;

    public PostController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    // 9. 게시글 작성
    @PostMapping("/post")
    public Response<Post> create(@RequestBody Post post, HttpServletRequest request) {
        try {
            Post findPost = postService.create(post, request);

            return new Response<>(findPost);
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 10. 게시글 리스트 조회
    @GetMapping("/posts")
    public Response<List<PostDTO>> posts(@PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        List<Post> posts = postService.findAll(pageable);
        List<PostDTO> postDTOs = new ArrayList<>();
        int postSize = postRepository.findAll().size();
        int lastPage = Math.max((int) Math.ceil(postSize / 5), 1);

        for (Post post : posts) {
            PostDTO postDTO = PostDTO.boardList(post);
            postDTOs.add(postDTO);
        }

        return new Response<>(postDTOs, lastPage);
    }

    // 11. 게시물 상세 조회
    @GetMapping("/post/{post_id}")
    public Response<PostDTO> post(@PathVariable("post_id") Long postId) {

        Post post = postService.getPost(postId).get();
        PostDTO postDTO = PostDTO.board(post);

        return new Response<>(postDTO);
    }

    // 12. 게시글 수정
    @PutMapping("/post/{post_id}")
    public Response<PostDTO> update(@PathVariable("post_id") Long postId, @RequestBody Post post, HttpServletRequest request) {

        try {
            Post updatedPost = postService.updatePost(postId, post, request);
            PostDTO postDTO = new PostDTO(updatedPost.getId(), updatedPost.getTitle(), updatedPost.getContent(), updatedPost.getCreatedAt(), updatedPost.getUpdatedAt(), updatedPost.getCommentSize(),
                    new UserDTO(updatedPost.getUser().getId(), updatedPost.getUser().getName(), updatedPost.getUser().getCreatedAt(), updatedPost.getUser().getUpdatedAt()));

            return new Response<>(postDTO);
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }

    // 16. 게시글 삭제
    @DeleteMapping("/post/{post_id}")
    public Response<ValidateResponse> delete(@PathVariable("post_id") Long postId, HttpServletRequest request) {

        try {
            postService.delete(postId, request);
            return new Response<>(new ValidateResponse("delete", "게시글 삭제"));
        } catch (APIError e) {
            return new Response<>(new ErrorResponse(e.getCode(), e.getMessage()));
        }

    }


}
