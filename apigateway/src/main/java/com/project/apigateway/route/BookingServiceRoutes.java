package com.project.apigateway.route;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions.circuitBreaker;
import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class BookingServiceRoutes {
    @Bean
    public RouterFunction<ServerResponse> bookingRoutes() {
        return route("booking-service")
                .route(RequestPredicates.POST("/api/v1/booking"), http())
                .before(uri("http://localhost:8081"))
                .filter(circuitBreaker(
                        "bookingServiceCircuitBreaker",
                        URI.create("forward:/fallbackRoute")))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> fallbackRoute() {
        return route("fallbackRoute")
                .route(RequestPredicates.path("/fallbackRoute"),
                        request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                                .body("Booking service is down"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> bookingServiceApiDocs() {
        return route("booking-service-api-docs")
                .route(RequestPredicates.path("/docs/bookingservice/v3/api-docs"), http())
                .before(uri("http://localhost:8081"))
                .filter(setPath("/v3/api-docs"))
                .build();
    }
}
