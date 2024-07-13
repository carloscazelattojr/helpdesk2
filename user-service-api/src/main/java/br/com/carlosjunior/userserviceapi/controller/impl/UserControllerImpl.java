package br.com.carlosjunior.userserviceapi.controller.impl;


import br.com.carlosjunior.userserviceapi.controller.UserController;
import br.com.carlosjunior.userserviceapi.entity.User;
import br.com.carlosjunior.userserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import models.responses.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<UserResponse> findById(String id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }
}
