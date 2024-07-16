package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With
public record CreateUserRequest(

        @Schema(description = "User name", example = "Carlos Junior")
        @NotEmpty(message = "Name cannot be empty")
        @Size(min = 3, max = 50, message = "Name must contain between {min} and {max} characters")
        String name,

        @Schema(description = "User email", example = "example@email.com")
        @Email(message = "Invalid email")
        @NotBlank(message = "Email cannot be empty")
        @Size(min = 6, max = 50, message = "Email must contain between {min} and {max} characters")
        String email,


        @Schema(description = "User password", example = "123456")
        @Size(min = 5, max = 50, message = "Password must contain between {min} and {max} characters")
        @NotBlank(message = "Password cannot be empty")
        String password,


        @Schema(description = "User Profile", example = "[\"ROLE_ADMIN\",\"ROLE_CUSTOMER\"]")
        Set<ProfileEnum> profiles
) {
}
