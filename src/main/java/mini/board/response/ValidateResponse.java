package mini.board.response;

public class ValidateResponse {

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ValidateResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
