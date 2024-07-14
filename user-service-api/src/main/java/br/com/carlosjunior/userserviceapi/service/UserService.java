package br.com.carlosjunior.userserviceapi.service;

import br.com.carlosjunior.userserviceapi.entity.User;
import br.com.carlosjunior.userserviceapi.mapper.UserMapper;
import br.com.carlosjunior.userserviceapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.requests.UpdateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse findById(final String id) {
        return mapper.fromEntity(find(id));
    }

    public void save(CreateUserRequest createUserRequest) {
        verifyIfEmailAlreadyExists(createUserRequest.email(), null);
        repository.save(
                mapper.fromRequest(createUserRequest)
                        .withPassword(passwordEncoder.encode(createUserRequest.password())));
    }


    public List<UserResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::fromEntity)
                .toList();
    }

    public UserResponse update(String id, UpdateUserRequest updateUserRequest) {
        User userEntity = find(id);
        verifyIfEmailAlreadyExists(updateUserRequest.email(), id);
        return mapper.fromEntity(repository.save(
                mapper.update(updateUserRequest, userEntity)
                        .withPassword(updateUserRequest.password() != null
                                ? passwordEncoder.encode(updateUserRequest.password())
                                : userEntity.getPassword())
        ));
    }

    private User find(final String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Object not found. Id: " + id + ", Type: " + UserResponse.class.getSimpleName()
        ));
    }

    private void verifyIfEmailAlreadyExists(final String email, final String id) {
        repository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("E-mail [ " + email + "] already exists");
                });
    }

}
