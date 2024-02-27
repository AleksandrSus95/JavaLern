package exceptions;

public class MethodNotAllowedExc extends AppException {
    public MethodNotAllowedExc(Integer code, String message) {
        super(code, message);
    }
}
