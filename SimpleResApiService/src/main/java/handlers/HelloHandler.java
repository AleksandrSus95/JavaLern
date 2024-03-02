package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import enums.StatusCode;
import exceptions.AppException;
import exceptions.AppExceptions;
import exceptions.GlobalExceptions;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static enums.Methods.GET;
import static enums.StatusCode.METHOD_NOT_ALLOWED;
import static enums.StatusCode.OK;
import static utils.ParamHelper.splitQuery;

public class HelloHandler extends Handler{
    public HelloHandler(GlobalExceptions exceptionsHandler, ObjectMapper objectMapper) {
        super(exceptionsHandler, objectMapper);
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        if (GET.name().equals(exchange.getRequestMethod())) {
            Map<String, List<String>> params = splitQuery(exchange.getRequestURI().getRawQuery());
            String defaultName = "Anonymous";
            String name = params.getOrDefault("name", List.of(defaultName)).stream().findFirst().orElse(defaultName);
            String responseText = String.format("Hello %s!", name);
            exchange.sendResponseHeaders(OK.getCode(), responseText.getBytes().length);
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(responseText.getBytes());
            outStream.flush();
            outStream.close();
        } else {
            throw AppExceptions.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }
        exchange.close();
    }
}
