package enums;

public enum StatusCode {
    OK(200),
    CREATED(201),
    ACCEPTED(202),
    BAD_REQUEST(400),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),
    SERVER_ERROR(500);

    private final int code;
    StatusCode(int code){
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
