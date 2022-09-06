package com.murilonerdx.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDTO {
    private String id;
    private String description;
    private Integer price;

    public ProductDTO(String description, Integer price) {
        this.description = description;
        this.price = price;
    }
}
