package com.murilonerdx.userservice.util;

import com.murilonerdx.userservice.dto.TransactionRequestDTO;
import com.murilonerdx.userservice.dto.TransactionResponseDTO;
import com.murilonerdx.userservice.dto.TransactionStatus;
import com.murilonerdx.userservice.dto.UserDTO;
import com.murilonerdx.userservice.entity.User;
import com.murilonerdx.userservice.entity.UserTransaction;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

public class EntityDTOUtil {

    public static UserDTO toDTO(User user) {
        UserDTO DTO = new UserDTO();
        BeanUtils.copyProperties(user, DTO);

        return DTO;
    }

    public static User toEntity(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        return user;
    }

    public static UserTransaction toEntity(TransactionRequestDTO requestDto){
        UserTransaction ut = new UserTransaction();
        ut.setUserId(requestDto.getUserId());
        ut.setAmount(requestDto.getAmount());
        ut.setTransactionDate(LocalDateTime.now());
        return ut;
    }

    public static TransactionResponseDTO toDTO(TransactionRequestDTO requestDto, TransactionStatus approved) {
        TransactionResponseDTO responseDto = new TransactionResponseDTO();
        responseDto.setAmount(requestDto.getAmount());
        responseDto.setUserId(requestDto.getUserId());
        responseDto.setStatus(approved);
        return responseDto;
    }
}
