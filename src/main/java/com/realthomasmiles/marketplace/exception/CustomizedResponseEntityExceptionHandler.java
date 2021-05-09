package com.realthomasmiles.marketplace.exception;

import com.realthomasmiles.marketplace.dto.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MarketPlaceException.EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFoundExceptions(Exception ex, WebRequest request) {
        Response<Object> response = Response.notFound();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MarketPlaceException.DuplicateEntityException.class)
    public final ResponseEntity<Object> handleEntityDuplicateExceptions(Exception ex, WebRequest request) {
        Response<Object> response = Response.duplicateEntity();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MarketPlaceException.UnauthorizedAccessToEntityException.class)
    public final ResponseEntity<Object> handleUnauthorizedAccessToEntityExceptions(Exception ex, WebRequest request) {
        Response<Object> response = Response.unauthorizedAccessToEntity();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
