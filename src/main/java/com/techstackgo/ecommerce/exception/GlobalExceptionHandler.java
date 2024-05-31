package com.techstackgo.ecommerce.exception;

import com.techstackgo.ecommerce.dto.DefaultResponseDto;
import com.techstackgo.ecommerce.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DefaultResponseDto methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("{}", e);
        e.printStackTrace();
        log.info("Illegal Argument Exception measssage:{}", e.getLocalizedMessage());
        if (e.getLocalizedMessage().contains("price null not allow")) {
            return handleValidationError(e, "price null not allow");
        }
        return new DefaultResponseDto(200, e.getMessage(), e.getLocalizedMessage(), "200");
    }

    private DefaultResponseDto handleValidationError(Exception e, String message) {
        // Handling Price Not Found Exception
        if (e.getLocalizedMessage().contains(message)) {
            return new DefaultResponseDto(400, message, message, e.getLocalizedMessage());
        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleGlobalException(Exception ex,
            WebRequest request) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

}
