package tech.pinto.catel.util.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.pinto.catel.util.Response;

@RestControllerAdvice
public class ErrHandler {

    @ExceptionHandler(OopsException.class)
    public Response errHandler(OopsException e) {
        return Response.buildFailure(e.getMessage());
    }

    @ExceptionHandler(EntityNotExists.class)
    public Response errHandler(EntityNotExists e) {
        return Response.buildFailure("实体( " + e.getEntityName() + " )不存在");
    }
}

