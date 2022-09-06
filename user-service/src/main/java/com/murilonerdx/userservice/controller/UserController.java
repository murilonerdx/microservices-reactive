package com.murilonerdx.userservice.controller;

import com.murilonerdx.userservice.dto.UserDTO;
import com.murilonerdx.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public Flux<UserDTO> all(){
        return this.service.all();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> getUserById(@PathVariable int id){
        return this.service.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<UserDTO> createUser(@RequestBody Mono<UserDTO> userDtoMono){
        return this.service.createUser(userDtoMono);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<UserDTO>> updateUser(@PathVariable int id, @RequestBody Mono<UserDTO> userDtoMono){
        return this.service.updateUser(id, userDtoMono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteUser(@PathVariable int id){
        return this.service.deleteUser(id);
    }

}
