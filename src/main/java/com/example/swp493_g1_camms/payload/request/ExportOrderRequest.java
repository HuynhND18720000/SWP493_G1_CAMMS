package com.example.swp493_g1_camms.payload.request;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExportOrderRequest {
    private Long user_Id;
    private ConsignmentRequest consignmentRequest;
    private List<ProductForExport> productForExport;
}
