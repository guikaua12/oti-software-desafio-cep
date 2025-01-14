package tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.guilhermekaua.otisoftwaredesafiocep.error.enums.ErrorCode;
import tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions.ErrorCodeException;

import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ErrorCodeException ex) {
        final ErrorCode errorCode = ex.getErrorCode();
        return new ResponseEntity<>(new ApiErrorResponse(errorCode.getCode(), errorCode.getStatusCode().value(), errorCode.getMessage()), errorCode.getStatusCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ConstraintViolationException ex) {
        final ConstraintViolation<?> violation = new ArrayList<>(ex.getConstraintViolations()).get(0);

        final ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;

        return new ResponseEntity<>(new ApiErrorResponse(errorCode.getCode(), errorCode.getStatusCode().value(), violation.getMessageTemplate()), errorCode.getStatusCode());
    }


}