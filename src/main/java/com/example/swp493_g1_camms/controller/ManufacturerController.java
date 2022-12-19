package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ManufacturerDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import com.example.swp493_g1_camms.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.swp493_g1_camms.services.interfaceService.IManufacturerService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/manufacturers")
@RestController
public class ManufacturerController {
    private final int defaultPage = 1;
    private final int defaultSize = 5;
    @Autowired
    private IManufacturerService manufacturerService;

    @Autowired
    Validation validation;

    @PostMapping("/addManufacturer")
    public ResponseEntity<?> addManufacturer(@RequestBody ManufacturerDTO manufacturerDTO) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        boolean checkEmailExist = validation.isEmailManufacturerExist(manufacturerDTO.getEmail());
        if(checkEmailExist == true){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email exist!", StatusUtils.NOT_Allow));
        }
        else {
            return manufacturerService.addManufacturer(manufacturerDTO);
        }
    }

    @GetMapping("/getAManufacturer")
    public ResponseEntity<?> getAManufacturer(Long id) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return manufacturerService.findManufacturerById(id);
    }


    @GetMapping
    public ResponseEntity<?> getListManufacturer(@RequestParam(required = false) Integer pageIndex,
                                                 @RequestParam(required = false) Integer pageSize) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return manufacturerService.findAllManufacturer(pageIndex, pageSize);
    }

    @GetMapping("/searchManufacturer")
    public ResponseEntity<?> SearchManufacturerByName(@RequestParam(required = false) Integer pageIndex,
                                                      @RequestParam(required = false) Integer pageSize,
                                                      @RequestParam(required = false) String name) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return manufacturerService.SearchManufacturer(pageIndex, pageSize, name);
    }

    @PutMapping("/editManufacturer")
    public ResponseEntity<?> editManufacturer(@RequestBody ManufacturerDTO manufacturerDTO) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return manufacturerService.editManufacturer(manufacturerDTO);
    }
    @GetMapping("/getManufacturerNotPagging")
    public ResponseEntity<?> getAllManufacturerNotPagging() {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return manufacturerService.getAllManufacturerNotPagging();
    }
}