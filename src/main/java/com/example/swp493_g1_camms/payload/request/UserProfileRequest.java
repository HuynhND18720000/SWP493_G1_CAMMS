package com.example.swp493_g1_camms.payload.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserProfileRequest {
    private Long user_id;
    private String full_name;
    private String email;
    private String phone;
    private String image;
    private String dob;
}
