package com.example.swp493_g1_camms.payload.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordRequest {
    private String new_password;
    private String confirm_password;
    private String otp;
}
