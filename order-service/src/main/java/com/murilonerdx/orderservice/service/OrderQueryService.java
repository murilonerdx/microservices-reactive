package com.murilonerdx.orderservice.service;

import com.murilonerdx.orderservice.dto.PurchaseOrderResponseDTO;
import com.murilonerdx.orderservice.repository.PurchaseOrderRepository;
import com.murilonerdx.orderservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Service
public class OrderQueryService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public Flux<PurchaseOrderResponseDTO> getProductsByUserId(int userId) {
        return Flux.fromIterable(this.purchaseOrderRepository.findByUserId(userId))
                .map(EntityDTOUtil::getPurchaseOrderResponseDTO)
                .subscribeOn(Schedulers.boundedElastic());
    }
}
