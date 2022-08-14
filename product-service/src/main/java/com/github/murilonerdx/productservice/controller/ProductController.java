package com.github.murilonerdx.productservice.controller;

import com.github.murilonerdx.productservice.dto.ProductDto;
import com.github.murilonerdx.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<ProductDto> all() {
        return
                service.getAll();
    }

    @GetMapping("/price-range")
    public Flux<ProductDto> all(@RequestParam("min") Integer min,
                                @RequestParam("max") Integer max) {
        return
                service.findByPriceBetween(min, max);
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> getById(@PathVariable("id") String id) {
        return
                service.getProductById(id)
                        .map(ResponseEntity::ok)
                        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ProductDto> inserProduct(@RequestBody Mono<ProductDto> productDtoMono) {
        return
                service.insertProduct(productDtoMono);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProduct(@PathVariable("id") String id,
                                                          @RequestBody Mono<ProductDto> productDto) {
        return
                service.updateProduct(id, productDto)
                        .map(ResponseEntity::ok)
                        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable("id") String id) {
        return
                service.deleteProduct(id)
                        .map(ResponseEntity::ok)
                        .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
