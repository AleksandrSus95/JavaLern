package exceptions;

public class AppException extends RuntimeException {

    private final Integer code;

    public AppException(Integer code, String message){
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
