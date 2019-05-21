package com.github.sanderploegsma.orders;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OrdersService extends OrdersGrpc.OrdersImplBase {
    private final Map<Long, Order> db;

    OrdersService() {
        this.db = new ConcurrentHashMap<Long, Order>();
        this.db.put(1L, Order.newBuilder().setId(1L).setQuantity(2L).build());
        this.db.put(2L, Order.newBuilder().setId(2L).setQuantity(6L).build());
        this.db.put(3L, Order.newBuilder().setId(3L).setQuantity(8L).build());
    }

    @Override
    public void getOrders(GetOrdersRequest request, StreamObserver<GetOrdersResponse> responseObserver) {
        GetOrdersResponse response = GetOrdersResponse
                .newBuilder()
                .addAllOrders(this.db.values())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getOrder(GetOrderRequest request, StreamObserver<GetOrderResponse> responseObserver) {
        Order product = this.db.get(request.getId());
        if (product == null) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            responseObserver.onCompleted();
            return;
        }

        GetOrderResponse response = GetOrderResponse
                .newBuilder()
                .setOrder(product)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
