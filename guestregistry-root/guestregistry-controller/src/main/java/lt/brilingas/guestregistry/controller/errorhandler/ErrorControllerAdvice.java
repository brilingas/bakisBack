package lt.brilingas.guestregistry.controller.errorhandler;

import lombok.extern.slf4j.Slf4j;
import lt.brilingas.guestregistry.dao.api.ParameterNotValidException;
import lt.brilingas.guestregistry.service.data.ResourceNotFoundException;
import lt.brilingas.guestregistry.service.data.FieldNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@Slf4j
@RestControllerAdvice
public class ErrorControllerAdvice {
    @ExceptionHandler({FieldNotValidException.class, ParameterNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleBadRequestValueNotValid(Exception ex) {
        ErrorResult errorResult = newErrorResult("Bad request due to error in syntax", ex);
        //log.info(errorResult.toString(), ex);
        ex.printStackTrace();
        return errorResult;
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult handleBadRequest(Exception ex) {
        ErrorResult errorResult = newErrorResult("Bad request due to error in syntax");
        //log.info(errorResult.toString(), ex);
        ex.printStackTrace();
        return errorResult;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResult handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResult errorResult = newErrorResult("Resource not found", ex);
        //log.info(errorResult.toString(), ex);
        ex.printStackTrace();
        return errorResult;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResult handleServerError(Exception ex) {
        ErrorResult errorResult = newErrorResult("Internal server error");
        //log.error(errorResult.toString(), ex);
        ex.printStackTrace();
        return errorResult;
    }

    private ErrorResult newErrorResult(String message, Exception ex) {
        StringBuilder builder = new StringBuilder(message);
        String exceptionMessage = ex.getMessage();
        if (exceptionMessage != null) {
            if (!exceptionMessage.isBlank()) {
                builder.append(": ").append(exceptionMessage);
            }
        }
        return newErrorResult(builder.toString());
    }

    private ErrorResult newErrorResult(String message) {
        ErrorResult error = new ErrorResult();
        error.setMessage(message);
        error.setErrorId(ErrorResult.getNextId());
        return error;
    }
}
