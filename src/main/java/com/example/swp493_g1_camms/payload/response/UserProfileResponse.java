package com.example.swp493_g1_camms.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {
    private long user_id;

    private String username;


    private String password;


    private String fullName;


    private String email;

    private String phone;

    private String image;

    private LocalDate dob;
}
