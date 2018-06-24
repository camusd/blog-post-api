package camusd.blogpostapi.controller;

import camusd.blogpostapi.BlogPostApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class BlogPostApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BlogPostApiException.class)
    public final ResponseEntity<ApiError> handleBlogPostApiException(BlogPostApiException e) {
        return new ResponseEntity<>(ApiError.newBuilder(e).build(), e.getStatusCode());
    }

    private static final class ApiError {
        private final BlogPostApiException.ErrorCode errorCode;
        private final LocalDateTime timestamp;
        private final String message;

        private ApiError(ApiErrorBuilder builder) {
            this.errorCode = builder.errorCode;
            this.timestamp = builder.timestamp;
            this.message = builder.message;
        }

        public BlogPostApiException.ErrorCode getErrorCode() {
            return errorCode;
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
            private BlogPostApiException.ErrorCode errorCode;
            private LocalDateTime timestamp;
            private String message;

            private ApiErrorBuilder(BlogPostApiException e) {
                this.errorCode = e.getErrorCode();
                this.timestamp = LocalDateTime.now();
                this.message = e.getMessage();
            }

            public ApiError build() {
                return new ApiError(this);
            }
        }
    }
}
