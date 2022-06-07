package ru.gontarenko.accountservice.rest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebExceptionHandler {
    private static final Map<String, String> CONSTRAINT_MESSAGES = new HashMap<>() {{
        put("account_email_idx", "Пользователь с такой эл. почтой уже существует.");
    }};

    ErrorAttributes errorAttributes;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolationException(WebRequest request, ConstraintViolationException exception) {
        val message = CONSTRAINT_MESSAGES.getOrDefault(
                exception.getConstraintName(),
                Optional.ofNullable(((PSQLException) exception.getSQLException()).getServerErrorMessage())
                        .map(ServerErrorMessage::getMessage)
                        .orElse("Empty error message")
        );
        val exceptionName = exception.getClass().getSimpleName();
        log.info("Handled bad request, exception: {}, message: {}", exceptionName, message);
        return getAttributes(request, HttpStatus.BAD_REQUEST, message);
    }

    private Map<String, Object> getAttributes(WebRequest request, HttpStatus httpStatus, String message) {
        request.setAttribute("javax.servlet.error.status_code", httpStatus.value(), RequestAttributes.SCOPE_REQUEST);
        val attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put("message", message);
        return attributes;
    }
}