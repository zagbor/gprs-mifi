package com.grps.order;

import com.grps.payment.OrderRequest;
import com.grps.payment.OrderResponse;
import com.grps.payment.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;

public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    private final OrderClient orderClient;

    public OrderServiceImpl() {
        // Инициализация клиента для взаимодействия с сервисом PaymentService
        this.orderClient = new OrderClient("localhost", 65535); // Указываем хост и порт сервиса оплаты
    }

    @Override
    public void createOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        System.out.println("Received order request:");
        System.out.println("Order ID: " + request.getOrderId());

        String orderId = request.getUserId();
        String userId = "USER123";  // Здесь можно использовать данные из запроса или базы данных
        double amount = 99.99;  // Например, это можно брать из запроса
        String currency = "USD";


        // Взаимодействуем с сервисом платежей через OrderClient
        orderClient.createOrder(orderId, userId, amount, currency);

        // Ответ на запрос
        OrderResponse response = OrderResponse.newBuilder()
                .setStatus("SUCCESS")  // Ответ может зависеть от результата запроса
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
