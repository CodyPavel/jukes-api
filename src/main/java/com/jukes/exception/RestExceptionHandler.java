package com.jukes.exception;

import com.jukes.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
class RestExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

    /**
     * Catch SettingsException
     *
     * @param exception that occurred due Settings loading process
     * @return returns ApiError object that contains HttpStatus, message and exception
     */
    @ExceptionHandler(SettingsException.class)
    ResponseEntity<ApiError> handleSettingsException(SettingsException exception) {
        String message = exception.getMessage();
        log.debug("handling exception::" + exception);
        if (message.contains("not found")) {
            return new ResponseEntity<>(ApiError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(LocalDateTime.now().format(formatter))
                    .message(message).build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now().format(formatter))
                .message(message).build(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Catch JukesException
     *
     * @param exception that occurred due Jukes loading process
     * @return returns ApiError object that contains HttpStatus, message and exception
     */
    @ExceptionHandler(JukesException.class)
    ResponseEntity<ApiError> handleJukesException(JukesException exception) {
        String message = exception.getMessage();
        log.debug("handling exception::" + exception);
        if (message.contains("not found")) {
            return new ResponseEntity<>(ApiError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .timestamp(LocalDateTime.now().format(formatter))
                    .message(message).build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now().format(formatter))
                .message(message).build(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Tend to catch unhandled exceptions in the program
     *
     * @param exception the exception that occurred unexpectedly
     * @return returns ApiError object that contains HttpStatus, message and exception
     */
    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> handleException(Exception exception) {
        String message = exception.getMessage();
        log.debug("handling exception::" + exception);
        return new ResponseEntity<>(ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now().format(formatter))
                .message(message).build(), HttpStatus.BAD_REQUEST);
    }
}