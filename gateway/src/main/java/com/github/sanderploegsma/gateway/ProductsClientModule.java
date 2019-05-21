package com.github.sanderploegsma.gateway;

import com.github.sanderploegsma.products.ProductsGrpc;
import com.github.sanderploegsma.products.ProductsServer;
import com.google.inject.AbstractModule;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class ProductsClientModule extends AbstractModule {
    protected void configure() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", ProductsServer.PORT).usePlaintext(true).build();
        bind(ProductsGrpc.ProductsBlockingStub.class).toInstance(ProductsGrpc.newBlockingStub(channel));
    }
}
