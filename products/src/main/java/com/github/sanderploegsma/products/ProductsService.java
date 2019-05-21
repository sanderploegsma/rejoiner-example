package com.github.sanderploegsma.products;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProductsService extends ProductsGrpc.ProductsImplBase {
    private final Map<Long, Product> db;

    ProductsService() {
        this.db = new ConcurrentHashMap<Long, Product>();
        this.db.put(1L, Product.newBuilder().setId(1L).setName("Product 1").build());
        this.db.put(2L, Product.newBuilder().setId(2L).setName("Product 2").build());
        this.db.put(3L, Product.newBuilder().setId(3L).setName("Product 3").build());
    }

    @Override
    public void getProducts(GetProductsRequest request, StreamObserver<GetProductsResponse> responseObserver) {
        GetProductsResponse response = GetProductsResponse
                .newBuilder()
                .addAllProducts(this.db.values())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(GetProductRequest request, StreamObserver<GetProductResponse> responseObserver) {
        Product product = this.db.get(request.getId());
        if (product == null) {
            responseObserver.onError(Status.NOT_FOUND.asException());
            responseObserver.onCompleted();
            return;
        }

        GetProductResponse response = GetProductResponse
                .newBuilder()
                .setProduct(product)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
