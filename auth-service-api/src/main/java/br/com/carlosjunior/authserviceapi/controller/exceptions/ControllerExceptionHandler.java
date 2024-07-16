package br.com.carlosjunior.authserviceapi.controller.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import models.exceptions.StandardError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<StandardError> handleNotFoundException(final BadCredentialsException ex, final HttpServletRequest request) {
        return ResponseEntity.status(UNAUTHORIZED).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(UNAUTHORIZED.value())
                        .error(UNAUTHORIZED.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<StandardError> handleDataIntegrityViolationException(final DataIntegrityViolationException ex, final HttpServletRequest request) {
        return ResponseEntity.status(CONFLICT).body(
                StandardError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(CONFLICT.value())
                        .error(CONFLICT.getReasonPhrase())
                        .message(ex.getMessage())
                        .path(request.getRequestURI())
                        .build()
        );
    }

}
