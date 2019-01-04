package com.rh.examples.demos.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 默认异常处理方法,返回异常请求路径和异常信息
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JSONObject defaultErrorHandler(HttpServletRequest request, Exception e){
        // TODO 加入业务逻辑

        JSONObject result = new JSONObject();
        result.put("code", "1");
        result.put("msg", e.getMessage());
        result.put("url", request.getRequestURI());
        return result;
    }

//    /**
//     * @ExceptionHandler 匹配抛出自定义的异常类型 MyException
//     * ErrorInfo<String> 为自定义的一个数据封装类，用于返回的json数据
//     * 如果返回的是json格式，需要加上@ResponsBody
//     */
//    @ExceptionHandler(value = MyException.class)
//    @ResponseBody
//    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest request, MyException e) throws Exception {
//
//        ErrorInfo<String> error = new ErrorInfo<>();
//        error.setCode(ErrorInfo.ERROR);
//        error.setMessage(e.getMessage());
//        error.setUrl(request.getRequestURI());
//        error.setData("未知错误");
//        return error;
//    }

}