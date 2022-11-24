package com.example.swp493_g1_camms.payload.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListConsignmentProductForCheckingRequest {
    private Long consignment_id;
    private Long wareHouseId;
    private LocalDateTime importDate;
    private String expirationDate;
    //quantity do nguoi dung nhap
    private int quantity;
    //quantity khi ma moi lan nhap luu lai
    private int quantity_in_import_consignment_product;
}
