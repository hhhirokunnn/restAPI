package teamlab.rest.error;

/**
 * @author magiccrafter
 * https://github.com/magiccrafter/spring-boot-exception-handling/blob/master/src/main/java/com/example/rest/error/ApiErrorResponse.java
 */
public class ApiErrorResponse {

    private int status;
    private int code;
    private String message;

    public ApiErrorResponse(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" +
                "status=" + status +
                ", code=" + code +
                ", message=" + message +
                '}';
    }
}