package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Staff;
import com.example.swp493_g1_camms.payload.request.StaffDTO;
import com.example.swp493_g1_camms.payload.response.ListStaffResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.IStaffRepository;
import com.example.swp493_g1_camms.services.interfaceService.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class StaffServiceImpl implements IStaffService {

    @Autowired
    IStaffRepository staffRepository;

    @Override
    public ResponseEntity<?> getAllStaff(int pageIndex, int pageSize, String staffName) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        String name = "";
        Page<Staff> staffPage = null;
        if (!ObjectUtils.isEmpty(staffName)) {
            name = staffName;
            staffPage = staffRepository.findBySearch(name.trim(), pageable);
        } else {
            staffPage = staffRepository.findAllByDeletedAt(false, pageable);
        }

        Map<String, Object> dataSearch = new HashMap<>();
        dataSearch.put("staffName", staffName);

        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        if (staffPage.isEmpty()) {
            map.put("staff", staffPage.getContent());
            map.put("totalRecord", 0);
            responseVo.setMessage("Không tìm thấy danh sách nhân viên!");
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        map.put("staff", ListStaffResponse.createSuccessData(staffPage.getContent()));
        map.put("searchData", dataSearch);
        map.put("currentPage", pageIndex);
        map.put("pageSize", pageSize);
        map.put("totalPage", staffPage.getTotalPages());
        map.put("totalRecord", staffPage.getTotalElements());
        responseVo.setData(map);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findStaffById(Long staffId) {
        Staff staff = staffRepository.getDetailStaff(staffId);
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        map.put("staff", staff);
        responseVo.setData(map);
        responseVo.setMessage("Lấy dữ liệu của staff thành công");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addStaff(StaffDTO staffDTO) {
        Staff staff = new Staff();
        staff.setFullName(staffDTO.getFullName());
        staff.setEmail(staffDTO.getEmail());
        staff.setPhone(staffDTO.getPhone());
        staff.setDob(staffDTO.getDob());
        staff.setRole(staffDTO.getRole());
        if(staffDTO.getImage() != null) {
            staff.setImage(staffDTO.getImage());
        }
        staff.setDeletedAt(false);
        staffRepository.save(staff);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Tạo nhân viên thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> editStaff(StaffDTO staffDTO) {
        Staff staff = staffRepository.getDetailStaff(staffDTO.getId());
        staff.setFullName(staffDTO.getFullName());
        staff.setEmail(staffDTO.getEmail());
        staff.setPhone(staffDTO.getPhone());
        staff.setDob(staffDTO.getDob());
        staff.setRole(staffDTO.getRole());
        staff.setImage(staffDTO.getImage());
        staff.setDeletedAt(false);
        staffRepository.save(staff);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Sửa thông tin nhân viên thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
}
