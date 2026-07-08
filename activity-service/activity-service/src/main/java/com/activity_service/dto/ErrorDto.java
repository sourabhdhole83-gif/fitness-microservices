package com.activity_service.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class ErrorDto {
    private   String message;
    private  String path;
    private  Integer status;



    public void setMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
