package com.murilonerdx.orderservice.util;

import com.murilonerdx.orderservice.dto.*;
import com.murilonerdx.orderservice.entity.PurchaseOrder;
import org.springframework.beans.BeanUtils;

public class EntityDTOUtil {

    public static void setTransactionRequestDTO(RequestContext requestContext){
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setUserId(requestContext.getPurchaseOrderRequestDTO().getUserId());
        dto.setAmount(requestContext.getProductDTO().getPrice());
        requestContext.setTransactionRequestDTO(dto);
    }

    public static PurchaseOrderResponseDTO getPurchaseOrderResponseDTO(PurchaseOrder purchaseOrder){
        PurchaseOrderResponseDTO dto = new PurchaseOrderResponseDTO();
        BeanUtils.copyProperties(purchaseOrder, dto);
        dto.setOrderId(purchaseOrder.getId());
        return dto;
    }

    public static void setTransactionRequestDto(RequestContext requestContext){
        TransactionRequestDTO dto = new TransactionRequestDTO();
        dto.setUserId(requestContext.getPurchaseOrderRequestDTO().getUserId());
        dto.setAmount(requestContext.getProductDTO().getPrice());
        requestContext.setTransactionRequestDTO(dto);
    }

    public static PurchaseOrder getPurchaseOrder(RequestContext requestContext){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(requestContext.getPurchaseOrderRequestDTO().getUserId());
        purchaseOrder.setProductId(requestContext.getPurchaseOrderRequestDTO().getProductId());
        purchaseOrder.setAmount(requestContext.getProductDTO().getPrice());
        TransactionStatus status = requestContext.getTransactionResponseDTO().getStatus();
        OrderStatus orderStatus = TransactionStatus.APPROVED.equals(status) ? OrderStatus.COMPLETED : OrderStatus.FAILED;
        purchaseOrder.setStatus(orderStatus);
        return purchaseOrder;
    }
}
