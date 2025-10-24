package lab.is.controllers;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessageResponseDto handleException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        Map<String, String> errors = violations.stream()
            .collect(
                Collectors.toMap(
                    v -> v.getPropertyPath().toString(),
                    ConstraintViolation::getMessage
                )
            );
        return ErrorMessageResponseDto.builder()
            .timestamp(new Date())
            .message("Передан объект неправильного формата")
            .violations(errors)
            .build();
    }
}
