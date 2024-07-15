package br.com.carlosjunior.userserviceapi.controller.impl;

import br.com.carlosjunior.userserviceapi.entity.User;
import br.com.carlosjunior.userserviceapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.carlosjunior.userserviceapi.creator.CreatorUtils.generateMock;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByIdWithSuccess() throws Exception {

        final var entity = generateMock(User.class);
        final var userId = repository.save(entity).getId();

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.password").value(entity.getPassword()))
                .andExpect(jsonPath("$.profiles").isArray());

        userRepository.delete(entity);
    }


    @Test
    void testFindByIdWithNotFoundException() throws Exception {
        final var userIdFake = "000";
        mockMvc.perform(get("/api/users/{id}", userIdFake))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Object not found. Id: " + userIdFake + ", Type: UserResponse"))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.path").value("/api/users/" + userIdFake))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    void testFindAllWithSuccess() throws Exception {
        final var entity1 = generateMock(User.class);
        final var entity2 = generateMock(User.class);

        userRepository.saveAll(List.of(entity1, entity2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[1]").isNotEmpty())

        ;
        userRepository.deleteAll(List.of(entity1, entity2));

    }

}