package com.example.hotel.util;

import com.example.hotel.vo.ResponseVO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrHandler {

    @ExceptionHandler(OopsException.class)
    public ResponseVO errHandler(OopsException e) {
        return ResponseVO.buildFailure(e.getMessage());
    }
}

