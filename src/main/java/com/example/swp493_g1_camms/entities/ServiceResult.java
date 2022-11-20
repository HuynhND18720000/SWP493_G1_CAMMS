package com.example.swp493_g1_camms.entities;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
public class ServiceResult<T> implements Serializable {
    private HttpStatus status;
    private String message;
    private int status1;
    private transient T data;


    public ServiceResult() {
    }

    public ServiceResult(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ServiceResult(String message, int status1) {
        this.message = message;
        this.status1 = status1;
    }

}