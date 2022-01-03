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
        String msg = "实体( " + e.getEntityName() + " )不存在";
        System.err.println(msg);
        return Response.buildFailure(msg);
    }

    @ExceptionHandler(EnumOutRange.class)
    public Response errHandler(EnumOutRange e) {
        String msg = "解析枚举 " + e.enumName + " 失败，输入值为 " + e.input;
        System.err.println(msg);
        return Response.buildFailure(msg);
    }

}

