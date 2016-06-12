package com.ekocaman.grpc.calculator;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class CalculatorServer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final int port;
    private final Server server;

    public CalculatorServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        this.server = serverBuilder
                .addService(CalculatorServiceGrpc.bindService(new CalculatorServiceImpl()))
                .build();
    }

    public CalculatorServer(int port) {
        this(ServerBuilder.forPort(port), port);
    }

    public void start() throws IOException {
        server.start();
        log.info("Server has started, listening on {}", port);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                System.err.println("Shutting down Calculator server");
                CalculatorServer.this.stop();
                System.err.println("Server shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        CalculatorServer server = new CalculatorServer(8081);
        server.start();
        server.blockUntilShutdown();
    }
}