package com.lh.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Result<T>{

    private String code;
    private String msg;
    private T data;


    public static <T> Result<T> ok(String msg,T data){
        return new Result<T>("200",msg,data);
    }

    public static <T> Result<T> err(String msg,T data){
        return new Result<T>("120409",msg,data);
    }

}
