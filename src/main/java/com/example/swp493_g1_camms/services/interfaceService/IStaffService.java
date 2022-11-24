package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.StaffDTO;
import org.springframework.http.ResponseEntity;

public interface IStaffService {

    ResponseEntity<?> getAllStaff(int pageIndex, int pageSize, String staffName);

    ResponseEntity<?> findStaffById(Long staffId);


    ResponseEntity<?> addStaff(StaffDTO staffDTO);

    ResponseEntity<?> editStaff(StaffDTO staffDTO);
}
