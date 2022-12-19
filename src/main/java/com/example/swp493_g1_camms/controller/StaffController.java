package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.StaffDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.services.interfaceService.IStaffService;
import com.example.swp493_g1_camms.utils.CurrentUserIsActive;
import com.example.swp493_g1_camms.utils.StatusUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/staff")
public class StaffController {
    private final int defaultPage = 1;
    private final int defaultSize = 10;

    @Autowired
    IStaffService staffService;

    @GetMapping
    public ResponseEntity<?> getListStaff(@RequestParam(required = false) Integer pageIndex,
                                          @RequestParam(required = false) Integer pageSize,
                                          @RequestParam(required = false) String staffName) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        return staffService.getAllStaff(pageIndex, pageSize,staffName);
    }

    @GetMapping("/getAStaff/{id}")
    public ResponseEntity<?> getAStaff(@PathVariable Long id) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return staffService.findStaffById(id);
    }

    @PostMapping("/addStaff")
    public ResponseEntity<?> addStaff(@RequestBody StaffDTO staffDTO) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return staffService.addStaff(staffDTO);
    }
    @PutMapping("/editStaff")
    public ResponseEntity<?> editStaff(@RequestBody StaffDTO staffDTO) {
        boolean isActive = CurrentUserIsActive.currentUserIsActive();
        if(!isActive){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Hết phiên làm việc", StatusUtils.NOT_Allow));
        }
        return staffService.editStaff(staffDTO);
    }
}
