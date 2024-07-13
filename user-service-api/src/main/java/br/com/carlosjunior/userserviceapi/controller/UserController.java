package br.com.carlosjunior.userserviceapi.controller;

import br.com.carlosjunior.userserviceapi.entity.User;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users")
public interface UserController {

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(@PathVariable(name = "id") String id);
}
