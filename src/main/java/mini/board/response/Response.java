package mini.board.response;

public class Response<T> {
    private T data;
    private ErrorResponse error;
    private ValidateResponse validate;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public ValidateResponse getValidate() {
        return validate;
    }

    public void setValidate(ValidateResponse validate) {
        this.validate = validate;
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(ErrorResponse error) {
        this.error = error;
    }

    public Response(ValidateResponse validate) {
        this.validate = validate;
    }


}
