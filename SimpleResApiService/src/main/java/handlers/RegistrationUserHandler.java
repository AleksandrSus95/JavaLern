package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import exceptions.GlobalExceptions;

public class RegistrationUserHandler extends Handler {

    public RegistrationUserHandler(GlobalExceptions exceptionsHandler, ObjectMapper objectMapper) {
        super(exceptionsHandler, objectMapper);
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {

    }
}
