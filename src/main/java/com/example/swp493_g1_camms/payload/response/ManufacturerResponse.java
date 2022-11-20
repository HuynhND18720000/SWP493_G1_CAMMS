package com.example.swp493_g1_camms.payload.response;

import lombok.*;

import javax.persistence.Column;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerResponse {
    private Long id;


    private String name;


    private String address;


    private String phone;


    private String email;

    private Boolean deletedAt;
}
