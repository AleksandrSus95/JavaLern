package exceptions;

public class InvalidRequestExc extends AppException {
    public InvalidRequestExc(Integer code, String message) {
        super(code, message);
    }
}
