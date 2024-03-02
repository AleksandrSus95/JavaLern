package exceptions;

public class ResourceNotFoundExc extends AppException {
    public ResourceNotFoundExc(Integer code, String message) {
        super(code, message);
    }
}
