package tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.handler;

import io.swagger.v3.oas.annotations.media.Schema;

public record ApiErrorResponse(
        @Schema(allowableValues = {"cep_not_found", "validation_error", "cep_already_exists", "incorrect_credentials", "user_already_exists"}) String errorCode,
        int status, String message) {
}
