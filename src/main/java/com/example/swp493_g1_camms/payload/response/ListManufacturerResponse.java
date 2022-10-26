package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListManufacturerResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    public static List<ListManufacturerResponse> createSuccessData(List<Manufacturer> manufacturers) {
        List<ListManufacturerResponse> listManufacturerResponses = new ArrayList<>();
        for(Manufacturer manufacturer : manufacturers){
            ListManufacturerResponse listManufacturerResponse = new ListManufacturerResponse();
            listManufacturerResponse.id = manufacturer.getId();
            listManufacturerResponse.address = manufacturer.getAddress();
            listManufacturerResponse.name = manufacturer.getName();
            listManufacturerResponse.phone = manufacturer.getPhone();
            listManufacturerResponse.email = manufacturer.getEmail();
            listManufacturerResponses.add(listManufacturerResponse);
        }
        return listManufacturerResponses;
    }
}
