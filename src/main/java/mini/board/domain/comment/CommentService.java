package mini.board.domain.comment;

import mini.board.domain.post.Post;
import mini.board.domain.post.PostRepository;
import mini.board.domain.user.User;
import mini.board.exception.APIError;
import mini.board.exception.NotLoginApiError;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Comment create(Long postId, Comment comment, HttpServletRequest request) {
        validate(comment, request);

        LocalDateTime date = LocalDateTime.now();

        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        Post findPost = postRepository.findById(postId).get();
        Long commentSize = findPost.getCommentSize();

        comment.setCreatedAt(date);
        comment.setUpdatedAt(date);
        comment.setUser(loggedUser);
        comment.setPost(findPost);
        findPost.setCommentSize(++commentSize);
        // XXX: 동시성 이슈있는데요. 프로시저

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment update(Long commentId, Comment comment, HttpServletRequest request) {
        updateValidate(commentId, comment, request);

        LocalDateTime date = LocalDateTime.now();
        Comment findComment = commentRepository.findById(commentId).get();

        findComment.setContent(comment.getContent());
        findComment.setUpdatedAt(date);

        return commentRepository.save(findComment);
    }

    @Transactional
    public void delete(Long commentId, HttpServletRequest request) {
        deleteValidate(commentId, request);
        Comment comment = em.createQuery("select c from Comment c join fetch c.post where c.id = : id", Comment.class)
                .setParameter("id", commentId)
                .getSingleResult();

        // FIXME : CommentService.findByCommentId(commentId);
        // FIXME : PostService.findByPostId(postId);

        Long commentSize = comment.getPost().getCommentSize();

        commentRepository.delete(comment);
        comment.getPost().setCommentSize(--commentSize);
    }

    private void validate(Comment comment, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");

        if (loggedUser == null) {
            throw new NotLoginApiError("로그인 유저가 아닙니다.");
        }

        if (comment.getContent().isBlank()) {
            throw new APIError("InvalidContent", "내용을 입력해주세요.");
        }

        if (comment.getContent().length() > 50) {
            throw new APIError("LimitContent", "50자 이하로 입력해주세요.");
        }

    }

    private void updateValidate(Long commentId, Comment comment, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");
        Long user_id = (Long) session.getAttribute("user_id");

        Comment findComment = commentRepository.findById(commentId).get();

        if (loggedUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (!(findComment.getUser().getId() == user_id)) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (comment.getContent().isBlank()) {
            throw new APIError("InvalidContent", "내용을 입력해주세요.");
        }

        if (comment.getContent().length() > 50) {
            throw new APIError("LimitContent", "50자 이하로 입력해주세요.");
        }

    }

    private void deleteValidate(Long commentId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute("loggedUser");
        Long user_id = (Long) session.getAttribute("user_id");

        Comment findComment = commentRepository.findById(commentId).get();

        if (loggedUser == null) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

        if (!(findComment.getUser().getId() == user_id)) {
            throw new APIError("NotLogin", "로그인 유저가 아닙니다.");
        }

    }

}
