package com.murilonerdx.orderservice.service;

import com.murilonerdx.orderservice.client.ProductClient;
import com.murilonerdx.orderservice.client.UserClient;
import com.murilonerdx.orderservice.dto.PurchaseOrderRequestDTO;
import com.murilonerdx.orderservice.dto.PurchaseOrderResponseDTO;
import com.murilonerdx.orderservice.dto.RequestContext;
import com.murilonerdx.orderservice.repository.PurchaseOrderRepository;
import com.murilonerdx.orderservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class OrderFulfillmentService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private UserClient userClient;

    public Mono<PurchaseOrderResponseDTO> processOrder(Mono<PurchaseOrderRequestDTO> requestDTOMono) {
        return requestDTOMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDTOUtil::setTransactionRequestDTO)
                .flatMap(this::userRequestResponse)
                .map(EntityDTOUtil::getPurchaseOrder)
                .map(this.purchaseOrderRepository::save) // Blocking
                .map(EntityDTOUtil::getPurchaseOrderResponseDTO)
                .subscribeOn(Schedulers.boundedElastic());
    }

    /*
        Timeout Pattern

        Retry Pattern

        Circuit Breaker Pattern

        Bulkhead Pattern

        Rate Limiter Pattern
     */

    private Mono<RequestContext> productRequestResponse(RequestContext rc) {
        return this.productClient.getProductById(rc.getPurchaseOrderRequestDTO().getProductId())
                .doOnNext(rc::setProductDTO)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    private Mono<RequestContext> userRequestResponse(RequestContext rc) {
        return this.userClient
                .authorizeTransaction(rc.getTransactionRequestDTO())
                .doOnNext(rc::setTransactionResponseDTO)
                .thenReturn(rc);
    }
}
