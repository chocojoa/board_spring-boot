package com.lollipop.board.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import static com.lollipop.board.jwt.exception.JwtExceptionUtil.getErrorMessage;

@RestControllerAdvice
@Log4j2
public class CustomControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e, HttpServletRequest request) {
        String message = "Requested resource not found.";
        return buildErrorResponse(e, request, HttpStatus.NOT_FOUND, message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String message = "Invalid input provided. Please check the data and try again.";
        List<ErrorField> errorList = buildErrorFieldList(e);
        ErrorResponse errorResponse = buildErrorResponse(request, message, errorList);
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        String message = "Illegal argument provided. Please review your request.";
        return buildErrorResponse(e, request, HttpStatus.NOT_ACCEPTABLE, message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        String message = "Data integrity violation occurred. The operation could not be completed.";
        return buildErrorResponse(e, request, HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
        String message = "Malformed JSON request. Please ensure the request body is correctly formatted.";
        return buildErrorResponse(e, request, HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        log.error(e.getMessage());
        String errorCode = e.getMessage();
        String message = getErrorMessage(errorCode);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .requestUrl(request.getRequestURI())
                .method(request.getMethod())
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception e, HttpServletRequest request, HttpStatus status, String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .requestUrl(request.getRequestURI())
                .method(request.getMethod())
                .build();
        log.error(e.getMessage());
        return ResponseEntity.status(status).body(errorResponse);
    }

    private ErrorResponse buildErrorResponse(HttpServletRequest request, String message, List<ErrorField> errorList) {
        return ErrorResponse.builder()
                .message(message)
                .errorList(errorList)
                .method(request.getMethod())
                .requestUrl(request.getRequestURI())
                .build();
    }

    private List<ErrorField> buildErrorFieldList(MethodArgumentNotValidException e) {
        List<ErrorField> errorList = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            ErrorField errorField = new ErrorField(fieldError.getField(), fieldError.getDefaultMessage(), String.valueOf(fieldError.getRejectedValue()));
            errorList.add(errorField);
        });
        errorList.sort(Comparator.comparing(ErrorField::getField));
        return errorList;
    }
}
