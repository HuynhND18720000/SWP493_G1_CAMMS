package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ManufacturerDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.utils.StatusUtils;
import com.example.swp493_g1_camms.services.impl.ValidCheckServiceImpl;
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

    @Autowired
    ValidCheckServiceImpl validCheckServiceImpl;

    @PostMapping("/addManufacturer")
    public ResponseEntity<?> addManufacturer(@RequestBody ManufacturerDTO manufacturerDTO) {
        boolean checkEmailExist = validCheckServiceImpl.isEmailManufacturerExist(manufacturerDTO.getEmail());
        if(checkEmailExist == true){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email exist!", StatusUtils.NOT_Allow));
        }
        else {
            return IManufacturerService.addManufacturer(manufacturerDTO);
        }
    }

    @GetMapping("/getAManufacturer")
    public ResponseEntity<?> getAManufacturer(Long id) {
        return IManufacturerService.findManufacturerById(id);
    }

    @GetMapping("/getAManufacturer1")
    public ResponseEntity<?> getA1Manufacturer(Long id) {
        return IManufacturerService.findAManufacturerById(id);
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
}
