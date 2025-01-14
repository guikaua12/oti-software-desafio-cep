package tech.guilhermekaua.otisoftwaredesafiocep.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import tech.guilhermekaua.otisoftwaredesafiocep.validation.Cep;

public record CreateCepDTO(@Cep String cep,
                           @NotBlank(message = "O logradouro é obrigatório.") @Size(max = 255, message = "O logradouro deve ter no máximo 255 caracteres.") String logradouro,
                           @NotBlank(message = "O bairro é obrigatório.") @Size(max = 255, message = "O bairro deve ter no máximo 255 caracteres.") String bairro,
                           @NotBlank(message = "A cidade é obrigatória.") @Size(max = 32, message = "A cidade deve ter no máximo 32 caracteres.") String cidade,
                           @NotBlank(message = "O estado é obrigatório.") @Size(max = 16, message = "O estado deve ter no máximo 16 caracteres.") String estado) {
}
