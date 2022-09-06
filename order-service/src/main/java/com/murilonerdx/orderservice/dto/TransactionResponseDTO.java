package com.murilonerdx.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponseDTO {

    private Integer userId;
    private Integer amount;
    private TransactionStatus status;
}
