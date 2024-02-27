package handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import exceptions.AppExceptions;
import exceptions.GlobalExceptions;

import java.io.OutputStream;

import static enums.Methods.GET;
import static enums.StatusCode.OK;

public class HelpHandler extends Handler {
    public HelpHandler(GlobalExceptions exceptionsHandler, ObjectMapper objectMapper) {
        super(exceptionsHandler, objectMapper);
    }

    @Override
    protected void execute(HttpExchange exchange) throws Exception {
        if (GET.name().equals(exchange.getRequestMethod()) && "/".equals(exchange.getRequestURI().getPath())) {
            String response = """
                    Test project to study REST API in Java.
                    """;
            exchange.sendResponseHeaders(OK.getCode(), response.getBytes().length);
            OutputStream outStream = exchange.getResponseBody();
            outStream.write(response.getBytes());
            outStream.flush();
            outStream.close();
        } else {
            if(!GET.name().equals(exchange.getRequestMethod()) && "/".equals(exchange.getRequestURI().getPath())){
                throw AppExceptions.methodNotAllowed(
                        "Method " + exchange.getRequestMethod() + " is not allowed for" + exchange.getRequestURI()).get();
            }
            throw AppExceptions.notFound(
                    "Resourse not found by Path " + exchange.getRequestURI()).get();
        }
        exchange.close();
    }
}
