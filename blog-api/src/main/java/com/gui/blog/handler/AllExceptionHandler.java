package com.gui.blog.handler;

import com.gui.blog.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//对加入 @ControllerAdvice注解的方法进行拦截处理 AOP的实现
@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler( Exception.class)
    @ResponseBody //返回json数据
    public Result  doException(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"服务器异常");
    }
}
