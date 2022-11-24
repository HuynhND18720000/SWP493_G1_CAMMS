package com.example.swp493_g1_camms.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String image;

    private String role;

    private LocalDate dob;
}
