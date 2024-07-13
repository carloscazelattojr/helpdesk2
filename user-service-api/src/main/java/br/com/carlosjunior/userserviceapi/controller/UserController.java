package br.com.carlosjunior.userserviceapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import models.exceptions.StandardError;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController", description = "Controller responsible for user operations")
@RequestMapping("/api/users")
public interface UserController {

    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "User id", required = true, example = "6691886ba9704548a17da12e")
            @PathVariable(name = "id") String id
    );

    @Operation(summary = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = StandardError.class)
                    ))
    })
    @PostMapping
    ResponseEntity<Void> salve(@RequestBody final CreateUserRequest createUserRequest);
}
