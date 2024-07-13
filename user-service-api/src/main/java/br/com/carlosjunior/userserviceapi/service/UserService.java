package br.com.carlosjunior.userserviceapi.service;

import br.com.carlosjunior.userserviceapi.mapper.UserMapper;
import br.com.carlosjunior.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(
                userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(
                        "Object not found. Id: "+ id + ", Type: "+UserResponse.class.getSimpleName()
                ))
        );
    }

}
