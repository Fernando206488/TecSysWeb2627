package edu.uclm.esi.tysweb.simulador.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import edu.uclm.esi.tysweb.simulador.dto.ErrorDto;
import edu.uclm.esi.tysweb.simulador.exceptions.RouteException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RouteException.class)
    public ResponseEntity<ErrorDto> handleRouteException(
            RouteException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorDto(e.getMessage()));
    }
}