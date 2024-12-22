package com.grps.payment;

import io.grpc.stub.StreamObserver;

public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Override
    public void processPayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        System.out.println("Processing payment:");
        System.out.println("Transaction ID: " + request.getTransactionId());
        System.out.println("User ID: " + request.getUserId());
        System.out.println("Amount: " + request.getAmount());
        System.out.println("Currency: " + request.getCurrency());

        // Имитация обработки платежа
        PaymentResponse response = PaymentResponse.newBuilder()
                .setStatus("SUCCESS")
                .setMessage("Payment processed successfully for transaction " + request.getTransactionId())
                .setTransactionId(request.getTransactionId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
