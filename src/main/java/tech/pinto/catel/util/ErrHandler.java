package tech.pinto.catel.util;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrHandler {

    @ExceptionHandler(OopsException.class)
    public Response errHandler(OopsException e) {
        return Response.buildFailure(e.getMessage());
    }
}

