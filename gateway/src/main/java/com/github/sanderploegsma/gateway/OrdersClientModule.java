package com.github.sanderploegsma.gateway;

import com.github.sanderploegsma.orders.OrdersGrpc;
import com.github.sanderploegsma.orders.OrdersServer;
import com.google.inject.AbstractModule;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class OrdersClientModule extends AbstractModule {
    protected void configure() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", OrdersServer.PORT).usePlaintext(true).build();
        bind(OrdersGrpc.OrdersBlockingStub.class).toInstance(OrdersGrpc.newBlockingStub(channel));
    }
}
