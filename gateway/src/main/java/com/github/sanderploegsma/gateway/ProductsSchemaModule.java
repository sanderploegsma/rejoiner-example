package com.github.sanderploegsma.gateway;

import com.github.sanderploegsma.products.*;
import com.google.api.graphql.rejoiner.Query;
import com.google.api.graphql.rejoiner.SchemaModule;

final class ProductsSchemaModule extends SchemaModule {
    @Query("getProducts")
    GetProductsResponse getProducts(GetProductsRequest request, ProductsGrpc.ProductsBlockingStub client) {
        return client.getProducts(request);
    }

    @Query("getProduct")
    GetProductResponse getProduct(GetProductRequest request, ProductsGrpc.ProductsBlockingStub client) {
        return client.getProduct(request);
    }
}
