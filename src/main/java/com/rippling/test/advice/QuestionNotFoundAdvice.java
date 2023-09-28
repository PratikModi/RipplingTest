package com.rippling.test.advice;

import com.rippling.test.exception.QuestionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class QuestionNotFoundAdvice {
    @ExceptionHandler({QuestionNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String questionNotFoundHandler(QuestionNotFoundException exception){
        return exception.getMessage();
    }
}
