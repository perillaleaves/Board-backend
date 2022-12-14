package mini.board.domain.user;

import mini.board.exception.APIError;

public class UserOverlapLoginRequest {

    String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public UserOverlapLoginRequest(String loginId) {
        this.loginId = loginId;
    }

    public void validate() {
        if (loginId.length() == 0) {
            throw new APIError("InvalidId", "아이디를 입력해주세요.");
        }
        if (loginId.length() < 8) {
            throw new APIError("LengthId", "아이디를 8글자 이상 입력해주세요.");
        }
    }
}
