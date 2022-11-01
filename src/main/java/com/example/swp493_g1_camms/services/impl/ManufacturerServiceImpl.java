package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Manufacturer;
import com.example.swp493_g1_camms.payload.request.ManufacturerDTO;
import com.example.swp493_g1_camms.payload.response.ListManufacturerResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.services.interfaceService.IManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.swp493_g1_camms.repository.IManufacturerRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class ManufacturerServiceImpl implements IManufacturerService {
    @Autowired
    private IManufacturerRepository IManufacturerRepository;

    @Override
    public ResponseEntity<?> findAllManufacturer(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<Manufacturer> manufacturerPage = null;
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        manufacturerPage = IManufacturerRepository.findAllManufacturerByDeletedAt(false, pageable);
        if (manufacturerPage.isEmpty()) {
            map.put("category", manufacturerPage.getContent());
            map.put("totalRecord", 0);
            responseVo.setMessage("Không tìm thấy danh sách nhà sản xuất!");
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        map.put("manufacturer", ListManufacturerResponse.createSuccessData(manufacturerPage.getContent()));
        map.put("currentPage", pageIndex);
        map.put("pageSize", pageSize);
        map.put("totalPage", manufacturerPage.getTotalPages());
        responseVo.setData(map);
        responseVo.setMessage("Lay du lieu thanh cong");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addManufacturer(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(9L);
        manufacturer.setName(manufacturerDTO.getName());
        manufacturer.setAddress(manufacturerDTO.getAddress());
        manufacturer.setEmail(manufacturerDTO.getEmail());
        manufacturer.setPhone(manufacturerDTO.getPhone());
        manufacturer.setDeletedAt(false);
        IManufacturerRepository.save(manufacturer);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Tạo nhà sản xuất thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> editManufacturer(ManufacturerDTO manufacturerDTO) {
        Manufacturer manufacturer1 = IManufacturerRepository.findManufacturerById(manufacturerDTO.getId());
        manufacturer1.setName(manufacturerDTO.getName());
        manufacturer1.setAddress(manufacturerDTO.getAddress());
        manufacturer1.setEmail(manufacturerDTO.getEmail());
        manufacturer1.setPhone(manufacturerDTO.getPhone());
        IManufacturerRepository.save(manufacturer1);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Sửa thông tin nhà sản xuất thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findManufacturerById(Long id) {
        Manufacturer manufacturer = IManufacturerRepository.findManufacturerById(id);
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        map.put("manufacturer", manufacturer);
        responseVo.setData(map);
        responseVo.setMessage("Lấy dữ liệu nhà nhà sản xuất thành công");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> SearchManufacturer(Integer pageIndex, Integer pageSize, String name) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<Manufacturer> manufacturerPage = null;
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        manufacturerPage = IManufacturerRepository.findAllManufacturerByName(name.trim(), pageable);
        if (manufacturerPage.isEmpty()) {
            map.put("category", manufacturerPage.getContent());
            map.put("totalRecord", 0);
            responseVo.setMessage("Không tìm thấy danh sách nhà sản xuất!");
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        map.put("manufacturer", ListManufacturerResponse.createSuccessData(manufacturerPage.getContent()));
        map.put("currentPage", pageIndex);
        map.put("pageSize", pageSize);
        map.put("totalPage", manufacturerPage.getTotalPages());
        responseVo.setData(map);
        responseVo.setMessage("Lay du lieu thanh cong");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

}
