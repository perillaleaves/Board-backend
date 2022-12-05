package mini.board.exception;

public class APIError extends Error {
    private String code;

    public String getCode() {
        return code;
    }

    public APIError(String code, String message) {
        super(message);
        this.code = code;
    }

}
