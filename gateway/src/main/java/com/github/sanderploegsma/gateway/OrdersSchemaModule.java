package com.github.sanderploegsma.gateway;

import com.github.sanderploegsma.orders.*;
import com.google.api.graphql.rejoiner.Query;
import com.google.api.graphql.rejoiner.SchemaModule;

final class OrdersSchemaModule extends SchemaModule {
    @Query("getOrders")
    GetOrdersResponse getOrders(GetOrdersRequest request, OrdersGrpc.OrdersBlockingStub client) {
        return client.getOrders(request);
    }

    @Query("getOrder")
    GetOrderResponse getOrder(GetOrderRequest request, OrdersGrpc.OrdersBlockingStub client) {
        return client.getOrder(request);
    }
}
