package com.example.blogpostapi;

import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Set;

/**
 * A custom runtime exception that indicates an error in the service layer and will propagate to transport layer.
 */
public class BlogPostApiException extends RuntimeException {

    public enum ErrorCode {
        INVALID_ARGUMENT
    }

    private final ErrorCode errorCode;
    private final HttpStatus statusCode;
    private final Set<? extends ConstraintViolation<?>> constraintViolations;

    private BlogPostApiException(BlogPostApiExceptionBuilder builder) {
        super(builder.message, builder.cause);
        this.errorCode = builder.errorCode;
        this.statusCode = builder.statusCode;
        this.constraintViolations = builder.constraintViolations;
    }

    public static BlogPostApiExceptionBuilder newBuilder(ErrorCode errorCode, HttpStatus statusCode, String message) {
        return new BlogPostApiExceptionBuilder(errorCode, statusCode, message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public Set<? extends ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }

    public static final class BlogPostApiExceptionBuilder {

        private ErrorCode errorCode;
        private HttpStatus statusCode;
        private String message;
        private Throwable cause;
        private Set<? extends ConstraintViolation<?>> constraintViolations;

        private BlogPostApiExceptionBuilder(ErrorCode errorCode, HttpStatus statusCode, String message) {
            this.errorCode = errorCode;
            this.statusCode = statusCode;
            this.message = message;
        }

        public BlogPostApiExceptionBuilder withCause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public BlogPostApiExceptionBuilder withConstraintViolations(Set<? extends ConstraintViolation<?>> constraintViolations) {
            this.constraintViolations = constraintViolations;
            return this;
        }

        public BlogPostApiException build() {
            if (this.constraintViolations == null) {
                this.constraintViolations = Collections.emptySet();
            }
            return new BlogPostApiException(this);
        }
    }

    public static BlogPostApiException invalidArgument(String message) {
        return BlogPostApiException.newBuilder(ErrorCode.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST, message).build();
    }

    public static BlogPostApiException invalidArgument(Throwable e) {
        return BlogPostApiException.newBuilder(ErrorCode.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST, e.getMessage())
                .withCause(e)
                .build();
    }

    public static BlogPostApiException invalidArgument(Set<? extends ConstraintViolation<?>> constraintViolations) {
        StringBuilder messageBuilder = new StringBuilder("Invalid Argument: ");
        for (ConstraintViolation constraintViolation : constraintViolations) {
            messageBuilder = messageBuilder.append("{field: ").append(constraintViolation.getPropertyPath().toString())
                    .append(", description: ").append(constraintViolation.getMessage()).append("},");
        }
        messageBuilder.setLength(messageBuilder.length() - 1);
        return BlogPostApiException.newBuilder(ErrorCode.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST, messageBuilder.toString())
                .withConstraintViolations(constraintViolations)
                .build();
    }
}
