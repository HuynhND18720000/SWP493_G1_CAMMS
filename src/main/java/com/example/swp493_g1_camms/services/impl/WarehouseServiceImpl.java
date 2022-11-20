package com.example.swp493_g1_camms.services.impl;


import com.example.swp493_g1_camms.entities.Manufacturer;
import com.example.swp493_g1_camms.entities.Warehouse;
import com.example.swp493_g1_camms.payload.request.WarehouseDTO;
import com.example.swp493_g1_camms.payload.response.ListWarehouseResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.IWarehouseRepository;
import com.example.swp493_g1_camms.services.interfaceService.IWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WarehouseServiceImpl implements IWarehouseService {

    @Autowired
    private IWarehouseRepository IWarehouseRepository;

    @Override
    public ResponseEntity<?> findAllWarehouse(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<Warehouse> warehousePage = null;
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        warehousePage = IWarehouseRepository.findAllWarehouseByDeletedAt(false, pageable);
        if (warehousePage.isEmpty()) {
            map.put("category", warehousePage.getContent());
            map.put("totalRecord", 0);
            responseVo.setMessage("Không tìm thấy danh sách nhà kho!");
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        map.put("warehouses", ListWarehouseResponse.createSuccessData(warehousePage.getContent()));
        map.put("currentPage", pageIndex);
        map.put("pageSize", pageSize);
        map.put("totalPage", warehousePage.getTotalPages());
        responseVo.setData(map);
        responseVo.setMessage("Lay du lieu thanh cong");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findWarehouseById(Long id) {
        Warehouse warehouse = IWarehouseRepository.findWarehouseById(id);
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        map.put("warehouse", ListWarehouseResponse.toWarehouseResponse(warehouse));
        responseVo.setData(map);
        responseVo.setMessage("Lấy dữ liệu nhà kho thành công!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> editWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = IWarehouseRepository.findWarehouseById(warehouseDTO.getId());
        warehouse.setName(warehouseDTO.getName());
        warehouse.setAddress(warehouseDTO.getAddress());
        IWarehouseRepository.save(warehouse);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Sửa thông tin kho thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> addWarehouse(WarehouseDTO warehouseDTO) {
        Warehouse warehouse = new Warehouse();
//        manufacturer.setId(9L);
        warehouse.setName(warehouseDTO.getName());
        warehouse.setAddress(warehouseDTO.getAddress());
        warehouse.setDeletedAt(false);
        IWarehouseRepository.save(warehouse);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Tạo nhà kho thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }



}
