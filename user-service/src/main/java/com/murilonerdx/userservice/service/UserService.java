package com.murilonerdx.userservice.service;

import com.murilonerdx.userservice.dto.UserDTO;
import com.murilonerdx.userservice.repository.UserRepository;
import com.murilonerdx.userservice.util.EntityDTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Flux<UserDTO> all(){
        return this.userRepository.findAll()
                .map(EntityDTOUtil::toDTO);
    }

    public Mono<UserDTO> getUserById(final int userId){
        return this.userRepository.findById(userId)
                .map(EntityDTOUtil::toDTO);
    }

    public Mono<UserDTO> createUser(Mono<UserDTO> userDtoMono){
        return userDtoMono
                .map(EntityDTOUtil::toEntity)
                .flatMap(this.userRepository::save)
                .map(EntityDTOUtil::toDTO);
    }

    public Mono<UserDTO> updateUser(int id, Mono<UserDTO> userDtoMono){
        return this.userRepository.findById(id)
                .flatMap(u -> userDtoMono
                        .map(EntityDTOUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.userRepository::save)
                .map(EntityDTOUtil::toDTO);
    }

    public Mono<Void> deleteUser(int id){
        return this.userRepository.deleteById(id);
    }

}
