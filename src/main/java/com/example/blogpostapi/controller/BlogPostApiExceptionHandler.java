package com.example.blogpostapi.controller;

import com.example.blogpostapi.BlogPostApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class BlogPostApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BlogPostApiException.class)
    public final ResponseEntity<ApiError> handleBlogPostApiException(BlogPostApiException e, WebRequest request) {
        return buildResponseEntity(ApiError.newBuilder(e).build());
    }

    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatusCode());
    }

    private static final class ApiError {
        private final HttpStatus statusCode;
        private final LocalDateTime timestamp;
        private final String message;

        private ApiError(ApiErrorBuilder builder) {
            this.statusCode = builder.statusCode;
            this.timestamp = builder.timestamp;
            this.message = builder.message;
        }

        public HttpStatus getStatusCode() {
            return statusCode;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getMessage() {
            return message;
        }

        public static ApiErrorBuilder newBuilder(BlogPostApiException e) {
            return new ApiErrorBuilder(e);
        }

        private static final class ApiErrorBuilder {
            private HttpStatus statusCode;
            private LocalDateTime timestamp;
            private String message;

            private ApiErrorBuilder(BlogPostApiException e) {
                this.statusCode = e.getStatusCode();
                this.timestamp = LocalDateTime.now();
                this.message = e.getMessage();
            }

            public ApiError build() {
                return new ApiError(this);
            }
        }
    }
}
