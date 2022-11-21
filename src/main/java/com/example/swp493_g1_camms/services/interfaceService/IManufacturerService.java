package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.ManufacturerDTO;
import org.springframework.http.ResponseEntity;

public interface IManufacturerService {

    ResponseEntity<?> findAllManufacturer(int pageIndex, int pageSize) ;

    ResponseEntity<?> addManufacturer(ManufacturerDTO manufacturerDTO);

    ResponseEntity<?> editManufacturer(ManufacturerDTO manufacturerDTO);

    ResponseEntity<?> findManufacturerById(Long id);

    ResponseEntity<?> SearchManufacturer(Integer pageIndex, Integer pageSize, String name);

    ResponseEntity<?> getAllManufacturerNotPagging();
}
