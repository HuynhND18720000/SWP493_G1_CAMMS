package com.example.swp493_g1_camms.payload.request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FillOTPRequest {
    private String otp;
}
