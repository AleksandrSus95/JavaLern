package com.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import exceptions.GlobalExceptions;
import handlers.HelloHandler;
import handlers.HelpHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExampleApplication {
    private static final int servicePort = 8081;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final GlobalExceptions globalExcHandler = new GlobalExceptions(objectMapper);
    private static final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(servicePort), 0);
        server.createContext("/api/hello", new HelloHandler(globalExcHandler, objectMapper)::handle);
        server.createContext("/",  new HelpHandler(globalExcHandler, objectMapper)::handle);
        server.setExecutor(threadPoolExecutor);
        server.start();
    }
}