package com.github.sanderploegsma.products;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class ProductsServer {

    public static final int PORT = 50051;

    private static final Logger logger = Logger.getLogger(ProductsServer.class.getName());
    private Server server;

    private void start() throws IOException {
        /* The port on which the server should run */
        server = ServerBuilder.forPort(PORT).addService(new ProductsService()).build().start();
        logger.info("Server started, listening on " + PORT);
        Runtime.getRuntime()
                .addShutdownHook(
                        new Thread() {
                            @Override
                            public void run() {
                                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                                ProductsServer.this.stop();
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
        final ProductsServer server = new ProductsServer();
        server.start();
        server.blockUntilShutdown();
    }
}
