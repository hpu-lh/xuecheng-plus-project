package com.lh.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ErrorResult{
    private String errCode;

    private String errMessage;


    public static ErrorResult create(String errCode,String errMessage){
        return new ErrorResult(errCode,errMessage);
    }
}
