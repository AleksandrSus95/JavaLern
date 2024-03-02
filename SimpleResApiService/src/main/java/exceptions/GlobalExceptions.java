package exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import model.exceptions.ErrorResponse;

import java.io.IOException;
import java.io.OutputStream;

import static enums.Constants.APPLICATION_JSON;
import static enums.Constants.CONTENT_TYPE;
import static enums.StatusCode.*;
import static model.exceptions.ErrorResponse.builder;
import static model.exceptions.ErrorResponse.ErrorBuilder;

public class GlobalExceptions {
    private final ObjectMapper objectMapper;

    public GlobalExceptions(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void handle(Throwable throwable, HttpExchange exchange) {
        try {
            throwable.printStackTrace(); //TODO заменить на логгер
            exchange.getResponseHeaders().set(CONTENT_TYPE, APPLICATION_JSON);
            ErrorResponse response = getErrorResponse(throwable, exchange);
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(objectMapper.writeValueAsBytes(response));
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ErrorResponse getErrorResponse(Throwable throwable, HttpExchange exchange) throws IOException {
        ErrorBuilder builder = builder();
        if (throwable instanceof InvalidRequestExc) {
            InvalidRequestExc exc = (InvalidRequestExc) throwable;
            builder.setMessage(exc.getMessage()).setCode(exc.getCode());
            exchange.sendResponseHeaders(BAD_REQUEST.getCode(), 0);
        } else if (throwable instanceof ResourceNotFoundExc) {
            ResourceNotFoundExc exc = (ResourceNotFoundExc) throwable;
            builder.setMessage(exc.getMessage()).setCode(exc.getCode());
            exchange.sendResponseHeaders(NOT_FOUND.getCode(), 0);
        } else if (throwable instanceof MethodNotAllowedExc) {
            MethodNotAllowedExc exc = (MethodNotAllowedExc) throwable;
            builder.setMessage(exc.getMessage()).setCode(exc.getCode());
            exchange.sendResponseHeaders(METHOD_NOT_ALLOWED.getCode(), 0);
        } else {
            builder.setMessage(throwable.getMessage()).setCode(SERVER_ERROR.getCode());
            exchange.sendResponseHeaders(SERVER_ERROR.getCode(), 0);
        }

        return builder.build();
    }

}
