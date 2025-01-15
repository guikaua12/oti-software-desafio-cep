package tech.guilhermekaua.otisoftwaredesafiocep.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "O username é obrigatório.") @Size(max = 255, message = "O username deve ter no máximo 255 caracteres.") String username,
        @NotBlank(message = "A senha é obrigatória.") @Size(max = 255, message = "A senha deve ter no máximo 255 caracteres.") String password) {
}
