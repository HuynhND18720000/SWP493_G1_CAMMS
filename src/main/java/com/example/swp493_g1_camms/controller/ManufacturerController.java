package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ManufacturerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.swp493_g1_camms.services.interfaceService.IManufacturerService;

@RestController
public class ManufacturerController {
    private final int defaultPage = 1;
    private final int defaultSize = 5;
    @Autowired
    private IManufacturerService IManufacturerService;

    @PostMapping("/addManufacturer")
    public ResponseEntity<?> addManufacturer(@RequestBody ManufacturerDTO manufacturerDTO) {
        return IManufacturerService.addManufacturer(manufacturerDTO);
    }

    @GetMapping("/getAManufacturer")
    public ResponseEntity<?> getAManufacturer(Long id) {
        return IManufacturerService.findManufacturerById(id);
    }

    @GetMapping("/manufacturers")
    public ResponseEntity<?> getListManufacturer(@RequestParam(required = false) Integer pageIndex,
                                                 @RequestParam(required = false) Integer pageSize) {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return IManufacturerService.findAllManufacturer(pageIndex, pageSize);
    }

    @GetMapping("/searchManufacturer")
    public ResponseEntity<?> SearchManufacturerByName(@RequestParam(required = false) Integer pageIndex,
                                                      @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) String name) {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return IManufacturerService.SearchManufacturer(pageIndex, pageSize, name);
    }

    @PutMapping("/editManufacturer")
    public ResponseEntity<?> editManufacturer(@RequestBody ManufacturerDTO manufacturerDTO) {
        return IManufacturerService.editManufacturer(manufacturerDTO);
    }

    @GetMapping("/manufacturersNotPaging")
    public ResponseEntity<?> getListManufacturerNotPaging() {
        return IManufacturerService.findAllManufacturerNotPaging();
    }
}
