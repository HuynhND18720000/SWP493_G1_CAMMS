package com.example.swp493_g1_camms.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class ConsignmentProductKey implements Serializable {
    @Column(name = "consignment_id")
    public Long consignmentid;
    @Column(name = "product_id")
    public Long productid;


}
