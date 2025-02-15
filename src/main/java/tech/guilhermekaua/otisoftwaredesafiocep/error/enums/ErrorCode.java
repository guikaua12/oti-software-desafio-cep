package tech.guilhermekaua.otisoftwaredesafiocep.error.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    CEP_NOT_FOUND("cep_not_found", HttpStatus.NOT_FOUND, "CEP não encontrado."),
    CEP_ALREADY_EXISTS("cep_already_exists", HttpStatus.CONFLICT, "Já existe um CEP com este valor."),
    VALIDATION_ERROR("validation_error", HttpStatus.BAD_REQUEST, "%validation_message%"),
    INCORRECT_CREDENTIALS("incorrect_credentials", HttpStatus.NOT_FOUND, "Usuário ou senha incorretos."),
    USER_ALREADY_EXISTS("user_already_exists", HttpStatus.CONFLICT, "Usuário já existente.");

    final String code;
    final HttpStatusCode statusCode;
    final String message;

    ErrorCode(String code, HttpStatusCode statusCode, String message) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }
}
