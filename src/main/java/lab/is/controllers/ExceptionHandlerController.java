package lab.is.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lab.is.dto.responses.ErrorMessageResponseDto;
import lab.is.exceptions.IncorrectDtoInRequestException;
import lab.is.exceptions.NestedObjectIsUsedException;
import lab.is.exceptions.NestedObjectNotFoundException;
import lab.is.exceptions.ObjectNotFoundException;
import lab.is.exceptions.ValueOverflowException;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageResponseDto handleException(ObjectNotFoundException e) {
        return ErrorMessageResponseDto.builder()
            .timestamp(new Date())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(NestedObjectNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessageResponseDto handleException(NestedObjectNotFoundException e) {
        return ErrorMessageResponseDto.builder()
            .timestamp(new Date())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(IncorrectDtoInRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageResponseDto handleException(IncorrectDtoInRequestException e) {
        return ErrorMessageResponseDto.builder()
            .timestamp(new Date())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(NestedObjectIsUsedException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessageResponseDto handleException(NestedObjectIsUsedException e) {
        return ErrorMessageResponseDto.builder()
            .timestamp(new Date())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(ValueOverflowException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessageResponseDto handleException(ValueOverflowException e) {
        return ErrorMessageResponseDto.builder()
            .timestamp(new Date())
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageResponseDto handleException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String, String> errors = new HashMap<>();
        fieldErrors.forEach(fieldError ->
            errors.put(
                fieldError.getField(),
                fieldError.getDefaultMessage()
            )
        );
        return ErrorMessageResponseDto.builder()
            .timestamp(new Date())
            .message("Передан объект неправильного формата")
            .violations(errors)
            .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageResponseDto handleException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        Map<String, String> errors = new HashMap<>();
        violations.forEach(violation ->
            errors.put(
                violation.getPropertyPath().toString(),
                violation.getMessage()
            )
        );
        return ErrorMessageResponseDto.builder()
            .timestamp(new Date())
            .message("Передан объект неправильного формата")
            .violations(errors)
            .build();
    }
}
