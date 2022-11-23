package com.example.swp493_g1_camms.payload.request;

import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductForExport {
    private Long product_id;
    private List<ListConsignmentProductExport> consignmentProductExportList;
}
