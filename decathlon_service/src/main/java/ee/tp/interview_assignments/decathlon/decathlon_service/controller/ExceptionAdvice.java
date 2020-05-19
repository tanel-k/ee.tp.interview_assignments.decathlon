package ee.tp.interview_assignments.decathlon.decathlon_service.controller;

import ee.tp.interview_assignments.decathlon.decathlon_service.controller.dto.ErrorDto;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.exception.InvalidInputException;
import ee.tp.interview_assignments.decathlon.decathlon_service.service.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handleNotFoundException(NotFoundException ex) {
        return ErrorDto.of(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidInputException.class)
    public ErrorDto handleInvalidInputException(InvalidInputException ex) {
        return ErrorDto.of(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorDto handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String message = String.format("Parameter `%s` is required.", ex.getParameterName());
        return ErrorDto.of(message);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = String.format("Parameter `%s` needs to be convertible to type: %s.",
                ex.getName(),
                ex.getParameter().getParameterType().getName()
        );
        return ErrorDto.of(message);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorDto handleAnyThrowable(Throwable t) {
        log.error("Unexpected error.", t);
        return ErrorDto.of("Unexpected error.");
    }
}
