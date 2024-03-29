package mini.board.domain.post;

import mini.board.domain.comment.Comment;
import mini.board.domain.comment.CommentRepository;
import mini.board.domain.login.LoginService;
import mini.board.domain.user.User;
import mini.board.domain.user.UserRepository;
import mini.board.exception.APIError;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private final LoginService loginService;

    public PostService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, LoginService loginService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.loginService = loginService;
    }

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Post create(Post post, HttpServletRequest request) {
        validate(post, request);

//        HttpSession session = request.getSession();
//        User loggedUser = (User) session.getAttribute("loggedUser");
//        Long user_id = (Long) session.getAttribute("user_id");
//
        LocalDateTime date = LocalDateTime.now();
        post.setCreatedAt(date);
        post.setUpdatedAt(date);
//        post.setUser(loggedUser);
        post.setCommentSize(0L);

        Post addPost = postRepository.save(post);

        return addPost;
    }

    @Transactional
    public List<Post> findAll(Pageable pageable) {

        return postRepository.findPosts(pageable);
    }

    @Transactional
    public Optional<Post> getPost(Long postId) {
        Post post = postRepository.findById(postId).get();

        return Optional.ofNullable(post);
    }

    @Transactional
    public Post updatePost(Long postId, Post post, HttpServletRequest request) {

        updateValidate(postId, post, request);
        LocalDateTime date = LocalDateTime.now();

        Post findPost = postRepository.findById(postId).get();
        findPost.setTitle(post.getTitle());
        findPost.setContent(post.getContent());
        findPost.setUpdatedAt(date);

        return postRepository.save(findPost);
    }

    @Transactional
    public void delete(Long postId, HttpServletRequest request) {
        deleteValidate(postId, request);

        Post findPost = postRepository.findById(postId).get();
        List<Comment> comments = findPost.getComments();

        if (comments.size() > 0) {
            commentRepository.deleteAll(comments);
        }
        postRepository.delete(findPost);
    }

    private void validate(Post post, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (post.getTitle().isBlank()) {
            throw new APIError("InvalidTitle", "제목을 입력해주세요.");
        }

        if (post.getTitle().length() > 30) {
            throw new APIError("LimitTitle", "30자 이하로 입력해주세요.");
        }

        if (post.getContent().isBlank()) {
            throw new APIError("InvalidContent", "내용을 입력해주세요.");
        }

        if (post.getContent().length() > 1000) {
            throw new APIError("LimitContent", "1000자 이하로 입력해주세요.");
        }

    }

    private void updateValidate(Long postId, Post post, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");
        Long user_id = (Long) session.getAttribute("user_id");

        Post findPost = postRepository.findById(postId).get();

        if (loggedUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (!(findPost.getUser().getId() == user_id)) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (post.getTitle().isBlank()) {
            throw new APIError("InvalidTitle", "제목을 입력해주세요.");
        }

        if (post.getTitle().length() > 30) {
            throw new APIError("LimitTitle", "30자 이하로 입력해주세요.");
        }

        if (post.getContent().isBlank()) {
            throw new APIError("InvalidContent", "내용을 입력해주세요.");
        }

        if (post.getContent().length() > 1000) {
            throw new APIError("LimitContent", "1000자 이하로 입력해주세요.");
        }

    }

    private void deleteValidate(Long postId, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        User loggedUser = (User) session.getAttribute("loggedUser");
//        Long user_id = (Long) session.getAttribute("user_id");
//
//        Post findPost = postRepository.findById(postId).get();
//
//        if (loggedUser == null) {
//            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
//        }
//
//        if (!(findPost.getUser().getId() == user_id)) {
//            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
//        }

    }


}
