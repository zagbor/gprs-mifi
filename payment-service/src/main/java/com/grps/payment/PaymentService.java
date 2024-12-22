package com.grps.payment;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class PaymentService {

    private Server server;

    private void start() throws IOException {
        int port = 65534;
        server = ServerBuilder.forPort(port)
                .addService(new PaymentServiceImpl())
                .build()
                .start();
        System.out.println("PaymentService started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** Shutting down PaymentService since JVM is shutting down");
            PaymentService.this.stop();
            System.err.println("*** PaymentService shut down");
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
        final PaymentService server = new PaymentService();
        server.start();
        server.blockUntilShutdown();
    }
}
