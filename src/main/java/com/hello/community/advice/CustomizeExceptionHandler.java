package com.hello.community.advice;

import com.alibaba.fastjson.JSON;
import com.hello.community.dto.ResultDTO;
import com.hello.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler
    ModelAndView handler(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response){
        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        if (contentType.equals("application/json")) {
            ResultDTO resultDTO;
            if (ex instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) ex);
            }else {
                resultDTO = ResultDTO.errorOf(5404,"抱歉,服务出走,请多尝试一下或返回主页!");
            }
            try {
                response.setContentType("application/json");
                response.setStatus(5200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException e){
            }
            return null;
        }else{
        if (status.is4xxClientError()) {
            model.addAttribute("message","请求不存在,换一个试试!");
        }else if (status.is5xxServerError()) {
            model.addAttribute("message","哈哈哈,网页错误!");
        } else if (ex instanceof CustomizeException) {
            model.addAttribute("message",ex.getMessage());
        }else {
            model.addAttribute("message","抱歉,服务出走,请多尝试一下或返回主页!");
        }
        return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
