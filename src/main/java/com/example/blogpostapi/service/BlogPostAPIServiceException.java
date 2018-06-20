package com.example.blogpostapi.service;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Set;

/**
 * A custom runtime exception that indicates an error in the service layer and will propagate to transport layer.
 */
public class BlogPostAPIServiceException extends RuntimeException {

    public enum ErrorCode {
        INVALID_ARGUMENT
    }

    private final ErrorCode errorCode;
    private final Set<? extends ConstraintViolation<?>> constraintViolations;

    private BlogPostAPIServiceException(BlogPostAPIServiceExceptionBuilder builder) {
        super(builder.message, builder.cause);
        this.errorCode = builder.errorCode;
        this.constraintViolations = builder.constraintViolations;
    }

    public static BlogPostAPIServiceExceptionBuilder newBuilder(ErrorCode errorCode, String message) {
        return new BlogPostAPIServiceExceptionBuilder(errorCode, message);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public Set<? extends ConstraintViolation<?>> getConstraintViolations() {
        return constraintViolations;
    }

    public static final class BlogPostAPIServiceExceptionBuilder {

        private ErrorCode errorCode;
        private String message;
        private Throwable cause;
        private Set<? extends ConstraintViolation<?>> constraintViolations;

        private BlogPostAPIServiceExceptionBuilder(ErrorCode errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }

        public BlogPostAPIServiceExceptionBuilder withCause(Throwable cause) {
            this.cause = cause;
            return this;
        }

        public BlogPostAPIServiceExceptionBuilder withConstraintViolations(Set<? extends ConstraintViolation<?>> constraintViolations) {
            this.constraintViolations = constraintViolations;
            return this;
        }

        public BlogPostAPIServiceException build() {
            if (this.constraintViolations == null) {
                this.constraintViolations = Collections.emptySet();
            }
            return new BlogPostAPIServiceException(this);
        }
    }

    public static BlogPostAPIServiceException invalidArgument(String message) {
        return BlogPostAPIServiceException.newBuilder(ErrorCode.INVALID_ARGUMENT, message).build();
    }

    public static BlogPostAPIServiceException invalidArgument(Throwable e) {
        return BlogPostAPIServiceException.newBuilder(ErrorCode.INVALID_ARGUMENT, e.getMessage()).withCause(e).build();
    }

    public static BlogPostAPIServiceException invalidArgument(Set<? extends ConstraintViolation<?>> constraintViolations) {
        StringBuilder messageBuilder = new StringBuilder("Invalid Argument: ");
        for (ConstraintViolation constraintViolation : constraintViolations) {
            messageBuilder = messageBuilder.append("{field: ").append(constraintViolation.getPropertyPath().toString())
                    .append(", description: ").append(constraintViolation.getMessage()).append("},");
        }
        messageBuilder.setLength(messageBuilder.length() - 1);
        return BlogPostAPIServiceException.newBuilder(ErrorCode.INVALID_ARGUMENT, messageBuilder.toString())
                .withConstraintViolations(constraintViolations)
                .build();
    }
}
