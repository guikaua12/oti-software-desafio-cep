package tech.guilhermekaua.otisoftwaredesafiocep.error.exceptions;

import lombok.Getter;
import org.springframework.web.client.HttpStatusCodeException;
import tech.guilhermekaua.otisoftwaredesafiocep.error.enums.ErrorCode;

@Getter
public class ErrorCodeException extends HttpStatusCodeException {
    protected final ErrorCode errorCode;

    public ErrorCodeException(ErrorCode errorCode) {
        super(errorCode.getStatusCode(), errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
