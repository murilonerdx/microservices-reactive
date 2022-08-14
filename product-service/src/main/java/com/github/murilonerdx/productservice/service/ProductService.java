package com.github.murilonerdx.productservice.service;

import com.github.murilonerdx.productservice.dto.ProductDto;
import com.github.murilonerdx.productservice.entity.Product;
import com.github.murilonerdx.productservice.repository.ProductRepository;
import com.github.murilonerdx.productservice.util.EntityDtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Flux<ProductDto> getAll() {
        return
                repository.findAll()
                        .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getProductById(String id) {
        return
                repository.findById(id)
                        .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insertProduct(Mono<ProductDto> productDtoMono) {
        return productDtoMono
                .map(EntityDtoUtil::toModel)
                .flatMap(repository::insert)
                .map(EntityDtoUtil::toDto);

    }

    public Mono<ProductDto> updateProduct(String id, Mono<ProductDto> productDtoMono) {
        return
                repository
                        .findById(id)
                        .flatMap(product -> productDtoMono
                                .map(EntityDtoUtil::toModel)
                                .doOnNext(e -> e.setId(id)))
                        .flatMap(repository::save)
                        .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return
                repository.deleteById(id);
    }

    public Flux<ProductDto> findByPriceBetween(int min, int max) {
        return
                repository.findByPriceBetween(Range.closed(min, max))
                        .map(EntityDtoUtil::toDto);
    }
}
