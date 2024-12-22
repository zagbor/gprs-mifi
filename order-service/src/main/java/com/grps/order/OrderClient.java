package com.grps.order;

import com.grps.payment.PaymentRequest;
import com.grps.payment.PaymentResponse;
import com.grps.payment.PaymentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class OrderClient {

    private final PaymentServiceGrpc.PaymentServiceBlockingStub paymentStub;

    public OrderClient(String host, int port) {
        // Создаём канал для связи с сервером
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // Для простоты без SSL
                .build();
        paymentStub = PaymentServiceGrpc.newBlockingStub(channel);
    }

    // Метод для создания заказа и обработки платежа
    public void createOrder(String orderId, String userId, double amount, String currency) {
        System.out.println("Creating order:");
        System.out.println("Order ID: " + orderId);
        System.out.println("User ID: " + userId);
        System.out.println("Amount: " + amount);
        System.out.println("Currency: " + currency);

        // Создаём запрос для обработки платежа
        PaymentRequest request = PaymentRequest.newBuilder()
                .setTransactionId(orderId)
                .setUserId(userId)
                .setAmount(amount)
                .setCurrency(currency)
                .build();

        // Отправляем запрос на сервис для обработки платежа
        PaymentResponse response = paymentStub.processPayment(request);

        // Выводим результат обработки платежа
        System.out.println("Payment Response:");
        System.out.println("Status: " + response.getStatus());
        System.out.println("Message: " + response.getMessage());
        System.out.println("Transaction ID: " + response.getTransactionId());

        if ("SUCCESS".equals(response.getStatus())) {
            System.out.println("Order " + orderId + " processed successfully with payment.");
        } else {
            System.out.println("Order " + orderId + " failed due to payment issues.");
        }
    }
}
