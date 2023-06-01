package graduation.shoewise.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 200: 요청 성공
     */
    SUCCESS(true, 200, "요청에 성공하였습니다."),


    /**
     *  3000: Response 오류
     */
    INVALID_USER_ID(false, 3000, "사용자를 찾을 수 없습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
