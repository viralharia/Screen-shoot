package com.vharia.screenshoot.exceptions;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        // System.out.println("in handleMissing..");

        ExceptionJSONInfo response = new ExceptionJSONInfo(request.getDescription(false), ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        ExceptionJSONInfo response = new ExceptionJSONInfo(request.getDescription(false), "Invalid path",
                HttpStatus.NOT_FOUND.value(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = { MalformedURLException.class, URISyntaxException.class })
    public ResponseEntity<Object> invalidURL(final Exception ex, final WebRequest request) {
        // System.out.println("in invalidURL..");
        ExceptionJSONInfo response = new ExceptionJSONInfo(request.getDescription(false), "Invalid url",
                HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = { ScreenShotServiceException.class })
    public ResponseEntity<Object> internalError(final Exception ex, final WebRequest request) {

        ExceptionJSONInfo response = new ExceptionJSONInfo(request.getDescription(false), "Error in taking screenshot",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}