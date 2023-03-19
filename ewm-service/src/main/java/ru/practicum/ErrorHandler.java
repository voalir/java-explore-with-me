package ru.practicum;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.dto.ApiErrorDto;
import ru.practicum.exception.AccessFailedException;
import ru.practicum.exception.NotFoundException;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorDto handleEntityNotFoundException(final NotFoundException e) {
        return new ApiErrorDto(
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()),
                e.getMessage(),
                "not found",
                ApiErrorDto.Status.STATUS_404_NOT_FOUND,
                LocalDateTime.now());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleValidate(final ValidationException e) {
        return new ResponseEntity<>(
                Map.of("validate error", e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorDto handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        return new ApiErrorDto(
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()),
                e.getMessage(),
                "value violation",
                ApiErrorDto.Status.STATUS_409_CONFLICT,
                LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiErrorDto handleAccessFailedException(final AccessFailedException e) {
        return new ApiErrorDto(
                Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()),
                e.getMessage(),
                "no access",
                ApiErrorDto.Status.STATUS_409_CONFLICT,
                LocalDateTime.now());
    }

}
