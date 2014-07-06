package be.milieuinfo.midas.rest.controller;

import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

/**
 * @since 12/06/14
 */
@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestErrorHandler.class);

    /**
     * Request error that can be caused by a validation fault or by a bug.
     *
     * @param ex      The exception thrown.
     * @param request The request that were sent.
     * @return The response to send back.
     */
    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class, NullPointerException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        final String bodyOfResponse = logAndGetResponseBody(ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    /**
     * Fatal error; system-integrity is likely to be compromised.
     * 'HTTP Internal Server Error'.
     *
     * @param ex      The exception thrown.
     * @param request The request that were sent.
     * @return The response to send back.
     */
    @ExceptionHandler(value = {OutOfMemoryError.class, IOException.class})
    protected ResponseEntity<Object> handleFatalError(RuntimeException ex, WebRequest request) {
        final String bodyOfResponse = logAndGetResponseBody(ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private String logAndGetResponseBody(final RuntimeException ex) {
        LOGGER.error("Handling _FATAL_ERROR_ : {} with root cause {}", ex, Throwables.getRootCause(ex));
        return "Root cause :" + Throwables.getRootCause(ex);
    }

}
