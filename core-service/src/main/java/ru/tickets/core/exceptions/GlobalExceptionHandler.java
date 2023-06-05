package ru.circledevs.tasks.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.circledevs.tasks.dto.AppError;
import ru.circledevs.tasks.dto.ResourceNotFoundExceptions;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchNotFoundException(ResourceNotFoundExceptions e){
        log.error(e.getMessage(),e);
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),e.getMessage()),HttpStatus.NOT_FOUND);
    }
}
