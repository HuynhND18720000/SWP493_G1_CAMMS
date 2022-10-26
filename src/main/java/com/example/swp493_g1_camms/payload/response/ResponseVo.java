package com.example.swp493_g1_camms.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVo {
    boolean status;
    String message;
    Object data;
}
