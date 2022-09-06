package com.murilonerdx.orderservice;

import com.murilonerdx.orderservice.client.ProductClient;
import com.murilonerdx.orderservice.client.UserClient;
import com.murilonerdx.orderservice.dto.ProductDTO;
import com.murilonerdx.orderservice.dto.PurchaseOrderRequestDTO;
import com.murilonerdx.orderservice.dto.PurchaseOrderResponseDTO;
import com.murilonerdx.orderservice.dto.UserDTO;
import com.murilonerdx.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class OrderServiceApplicationTests {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderFulfillmentService fulfillmentService;

    @Test
    void contextLoads() {

        Flux<PurchaseOrderResponseDTO> dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
                .map(t -> buildDto(t.getT1(), t.getT2()))
                .flatMap(dto -> this.fulfillmentService.processOrder(Mono.just(dto)))
                .doOnNext(System.out::println);

        StepVerifier.create(dtoFlux)
                .expectNextCount(4)
                .verifyComplete();

    }

    private PurchaseOrderRequestDTO buildDto(UserDTO userDto, ProductDTO productDto){
        PurchaseOrderRequestDTO dto = new PurchaseOrderRequestDTO();
        dto.setUserId(userDto.getId());
        dto.setProductId(productDto.getId());
        return dto;
    }

}
