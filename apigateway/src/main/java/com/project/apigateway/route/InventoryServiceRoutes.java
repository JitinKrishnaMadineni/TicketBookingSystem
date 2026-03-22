package com.project.apigateway.route;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class InventoryServiceRoutes {
    @Bean
    public RouterFunction<ServerResponse> inventoryRoutes() {
        return route("inventory-service")
                .route(RequestPredicates.path("/api/v1/inventory/venue/{venueId}"), http())
                .before(uri("http://localhost:8080"))
                .route(RequestPredicates.path("/api/v1/inventory/event/{eventId}"), http())
                .before(uri("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceApiDocs() {
        return route("inventory-service-api-docs")
                .route(RequestPredicates.path("/docs/inventoryservice/v3/api-docs"), http())
                .before(uri("http://localhost:8080"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
}
