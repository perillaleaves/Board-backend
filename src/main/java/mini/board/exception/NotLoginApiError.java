package mini.board.exception;

public class NotLoginApiError extends APIError {

    public NotLoginApiError(String message) {
        super("NotLogin", message);
    }
}