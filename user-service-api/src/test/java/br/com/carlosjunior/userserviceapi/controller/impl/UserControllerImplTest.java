package br.com.carlosjunior.userserviceapi.controller.impl;

import br.com.carlosjunior.userserviceapi.entity.User;
import br.com.carlosjunior.userserviceapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static br.com.carlosjunior.userserviceapi.creator.CreatorUtils.generateMock;
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

}