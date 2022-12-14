package mini.board.response;

public class Response<T> {

    private T data;
    private ErrorResponse error;
    private ValidateResponse validate;
    private int lastPage;

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

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(T data, int lastPage) {
        this.data = data;
        this.lastPage = lastPage;
    }

    public Response(ErrorResponse error) {
        this.error = error;
    }

    public Response(ValidateResponse validate) {
        this.validate = validate;
    }


}
