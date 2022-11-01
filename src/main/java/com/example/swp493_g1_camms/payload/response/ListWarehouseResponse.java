package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListWarehouseResponse {
    private Long id;
    private String name;
    private String address;


    public static List<ListWarehouseResponse> createSuccessData(List<Warehouse> warehouses) {
        List<ListWarehouseResponse> listWarehouseResponses = new ArrayList<>();
        for(Warehouse warehouse : warehouses){
            ListWarehouseResponse listWarehouseResponse = new ListWarehouseResponse();
            listWarehouseResponse.id = warehouse.getId();
            listWarehouseResponse.name = warehouse.getName();
            listWarehouseResponse.address = warehouse.getAddress();
            listWarehouseResponses.add(listWarehouseResponse);
        }
        return listWarehouseResponses;
    }

    public static ListWarehouseResponse toWarehouseResponse(Warehouse warehouse) {
        ListWarehouseResponse warehouseResponse = new ListWarehouseResponse();
        warehouseResponse.setId(warehouse.getId());
        warehouseResponse.setName(warehouse.getName());
        warehouseResponse.setAddress(warehouse.getAddress());
        return warehouseResponse;
    }
}
