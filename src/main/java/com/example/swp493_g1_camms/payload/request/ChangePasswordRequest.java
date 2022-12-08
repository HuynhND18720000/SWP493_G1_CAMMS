package com.example.swp493_g1_camms.payload.request;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    private String current_pass;
    private String new_pass;
    private String confirm_pass;
    private Long user_id;
}
