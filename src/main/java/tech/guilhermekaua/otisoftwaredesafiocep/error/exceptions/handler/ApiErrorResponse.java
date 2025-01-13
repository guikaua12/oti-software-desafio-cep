package tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.handler;

public record ApiErrorResponse(String errorCode, int status, String message) {
}
