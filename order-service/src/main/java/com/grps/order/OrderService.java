package com.grps.order;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class OrderService {

    private Server server;

    private void start() throws IOException {
        int port = 65535;
        server = ServerBuilder.forPort(port)
                .addService(new OrderServiceImpl())
                .build()
                .start();
        System.out.println("OrderService started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** Shutting down OrderService since JVM is shutting down");
            OrderService.this.stop();
            System.err.println("*** OrderService shut down");
        }));
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
        final OrderService server = new OrderService();
        server.start();
        server.blockUntilShutdown();
    }
}
