package com.murilonerdx.orderservice.controller;


import com.murilonerdx.orderservice.dto.PurchaseOrderRequestDTO;
import com.murilonerdx.orderservice.dto.PurchaseOrderResponseDTO;
import com.murilonerdx.orderservice.entity.PurchaseOrder;
import com.murilonerdx.orderservice.service.OrderFulfillmentService;
import com.murilonerdx.orderservice.service.OrderQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class PurchaseOrderController {

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Autowired
    private OrderQueryService orderQueryService;

    @GetMapping("user/{id}")
    public Flux<PurchaseOrderResponseDTO> getOrdersByUserId(@PathVariable int userId){
        return this.orderQueryService.getProductsByUserId(userId);
    }

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDTO>> order(@RequestBody Mono<PurchaseOrderRequestDTO> requestDTOMono){
        return this.orderFulfillmentService.processOrder(requestDTOMono)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build())
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
    }
}
