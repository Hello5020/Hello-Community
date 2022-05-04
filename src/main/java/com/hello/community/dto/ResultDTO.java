package com.hello.community.dto;

import com.hello.community.exception.CustomizeErrorCode;
import com.hello.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode){
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException errorCode){
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO successOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(5200);
        resultDTO.setMessage("success");
        return resultDTO;
    }
    public static <T> ResultDTO successOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(5200);
        resultDTO.setMessage("success");
        resultDTO.setData(t);
        return resultDTO;
    }

}
