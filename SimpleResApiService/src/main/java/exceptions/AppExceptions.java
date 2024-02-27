package exceptions;

import java.util.function.Function;
import java.util.function.Supplier;

import static enums.StatusCode.*;

public class AppExceptions {
    public static Function<? super Throwable, RuntimeException> invalidRequest() {
        return throwable -> new InvalidRequestExc(BAD_REQUEST.getCode(), throwable.getMessage());
    }

    public static Supplier<RuntimeException> methodNotAllowed(String message) {
        return () -> new MethodNotAllowedExc(METHOD_NOT_ALLOWED.getCode(), message);
    }

    public static Supplier<RuntimeException> notFound(String message) {
        return () -> new ResourceNotFoundExc(NOT_FOUND.getCode(), message);
    }
}
