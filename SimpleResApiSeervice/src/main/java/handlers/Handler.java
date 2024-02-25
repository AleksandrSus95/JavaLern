package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import exceptions.GlobalExceptions;

public abstract class Handler {

    private final GlobalExceptions exceptionsHandler;
    private final ObjectMapper objectMapper;

    public Handler(GlobalExceptions exceptionsHandler, ObjectMapper objectMapper) {
        this.exceptionsHandler = exceptionsHandler;
        this.objectMapper = objectMapper;
    }

    protected abstract void execute(HttpExchange exchange) throws Exception;

    public void handle(HttpExchange exchange) {
        try {
            execute(exchange);
        } catch (Exception e) {
            exceptionsHandler.handle(e, exchange);
        }
    }
}
