package mini.board.domain.post;

import mini.board.domain.comment.Comment;
import mini.board.domain.comment.CommentRepository;
import mini.board.domain.login.LoginService;
import mini.board.domain.user.User;
import mini.board.exception.APIError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final LoginService loginService;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, LoginService loginService, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.loginService = loginService;
        this.commentRepository = commentRepository;
    }

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Post create(Post post, HttpServletRequest request) {
        validate(post, request);

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        LocalDateTime date = LocalDateTime.now();
        post.setCreatedAt(date);
        post.setUpdatedAt(date);
        post.setUser(loginUser);
        post.setCommentSize(0L);

        Post addPost = postRepository.save(post);

        return addPost;
    }

    @Transactional
    public List<Post> getPosts() {

        return em.createQuery("select p from Post p join fetch p.user", Post.class)
                .getResultList();
    }

    @Transactional
    public Optional<Post> getPost(Post post) {

        Post getPost = em.createQuery("select p from Post p join fetch p.user where p.id = : id", Post.class)
                .setParameter("id", post.getId())
                .getSingleResult();

        return Optional.ofNullable(getPost);
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

//        Post findPost = em.createQuery("select p from Post p join fetch p.comments where p.id = : id", Post.class)
//                .setParameter("id", postId)
//                .getSingleResult();
//        List<Comment> comments = findPost.getComments();

        Post findPost = postRepository.findById(postId).get();
        List<Comment> comments = findPost.getComments();
        if (comments.size() > 0) {
            commentRepository.deleteAll(comments);
            postRepository.delete(findPost);
        } else {
            postRepository.delete(findPost);
        }

    }


    private void validate(Post post, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (post.getTitle().isBlank()) {
            throw new APIError("InvalidTitle", "제목을 입력해주세요.");
        }

        if (post.getTitle().length() > 30) {
            throw new APIError("limitTitle", "30자 이하로 입력해주세요.");
        }

        if (post.getContent().isBlank()) {
            throw new APIError("InvalidContent", "내용을 입력해주세요.");
        }

        if (post.getContent().length() > 1000) {
            throw new APIError("limitContent", "1000자 이하로 입력해주세요.");
        }

    }

    private void updateValidate(Long postId, Post post, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        Long userId = (Long) session.getAttribute("userId");

        Post findPost = postRepository.findById(postId).get();

        if (loginUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (!(findPost.getUser().getId() == userId)) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (post.getTitle().isBlank()) {
            throw new APIError("InvalidTitle", "제목을 입력해주세요.");
        }

        if (post.getTitle().length() > 30) {
            throw new APIError("limitTitle", "30자 이하로 입력해주세요.");
        }

        if (post.getContent().isBlank()) {
            throw new APIError("InvalidContent", "내용을 입력해주세요.");
        }

        if (post.getContent().length() > 1000) {
            throw new APIError("limitContent", "1000자 이하로 입력해주세요.");
        }

    }

    private void deleteValidate(Long postId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        Long userId = (Long) session.getAttribute("userId");

        Post findPost = postRepository.findById(postId).get();

        if (loginUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (!(findPost.getUser().getId() == userId)) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

    }


}
