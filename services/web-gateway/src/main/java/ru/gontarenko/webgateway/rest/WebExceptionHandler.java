package ru.gontarenko.webgateway.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WebExceptionHandler {
    ErrorAttributes errorAttributes;
    ObjectMapper objectMapper;

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FeignException.NotFound.class)
    public Map<String, Object> handleFeignNotFound(WebRequest request, FeignException.NotFound exception) {
        return getAttributes(
                request,
                exception.responseBody(),
                HttpStatus.NOT_FOUND,
                exception.getClass().getSimpleName()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FeignException.BadRequest.class)
    public Map<String, Object> handleFeignBadRequest(WebRequest request, FeignException.BadRequest exception) {
        return getAttributes(
                request,
                exception.responseBody(),
                HttpStatus.BAD_REQUEST,
                exception.getClass().getSimpleName()
        );
    }

    private Map<String, Object> getAttributes(
            WebRequest request,
            Optional<ByteBuffer> responseBody,
            HttpStatus httpStatus,
            String exceptionName
    ) {
        val message = responseBody.map(ByteBuffer::array)
                .map(this::convert)
                .map(error -> error.get("message").asText())
                .orElse("");
        request.setAttribute("javax.servlet.error.status_code", httpStatus.value(), RequestAttributes.SCOPE_REQUEST);
        val attributes = errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put("message", message);
        log.info("Handle exception: {}, message: {}", exceptionName, message);
        return attributes;
    }

    @SneakyThrows
    private ObjectNode convert(byte[] bytes) {
        return objectMapper.readValue(bytes, ObjectNode.class);
    }
}
