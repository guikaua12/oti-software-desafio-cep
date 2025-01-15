package tech.guilhermekaua.otisoftwaredesafiocep.dtos.cep;

import jakarta.validation.constraints.Size;
import tech.guilhermekaua.otisoftwaredesafiocep.validation.Cep;

public record UpdateCepDTO(@Cep String cep,
                           @Size(max = 255, message = "O logradouro deve ter no máximo 255 caracteres.") String logradouro,
                           @Size(max = 255, message = "O bairro deve ter no máximo 255 caracteres.") String bairro,
                           @Size(max = 32, message = "A cidade deve ter no máximo 32 caracteres.") String cidade,
                           @Size(max = 16, message = "O estado deve ter no máximo 16 caracteres.") String estado) {
}
