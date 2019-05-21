package com.github.sanderploegsma.orders;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class OrdersServer {

    public static final int PORT = 50052;

    private static final Logger logger = Logger.getLogger(OrdersServer.class.getName());
    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        server = ServerBuilder.forPort(PORT).addService(new OrdersService()).build().start();
        logger.info("Server started, listening on " + PORT);
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread() {
                            @Override
                            public void run() {
                                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                                OrdersServer.this.stop();
                                System.err.println("*** server shut down");
                            }
                        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final OrdersServer server = new OrdersServer();
        server.start();
        server.blockUntilShutdown();
    }
}
