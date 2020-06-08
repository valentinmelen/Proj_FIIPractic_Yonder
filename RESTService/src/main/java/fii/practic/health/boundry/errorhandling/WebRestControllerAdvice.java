package fii.practic.health.boundry.errorhandling;

import fii.practic.health.boundry.dto.ErrorDTO;
import fii.practic.health.boundry.exceptions.BadRequestException;
import fii.practic.health.boundry.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class WebRestControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFoundException(NotFoundException ex){
        return this.generateErrorDTO(HttpStatus.NOT_FOUND, ex);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequestException(BadRequestException ex){
        return this.generateErrorDTO(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO defaultHandler(Exception ex){
        return this.generateErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDTO constraintViolationHandler(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(String.format("%s -> %s", error.getField(), error.getDefaultMessage()));
        }
        return new ErrorDTO(HttpStatus.BAD_REQUEST.value(), builder.toString());
    }

    private ErrorDTO generateErrorDTO(HttpStatus httpStatus, Exception ex){
        return new ErrorDTO(httpStatus.value(), ex.getMessage());
    }

}
