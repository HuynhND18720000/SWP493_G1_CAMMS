package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ReturnOrderDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IReturnOderService {
    ResponseEntity<?> createReturnOrder(ReturnOrderDTO returnOrderDTO);

    ServiceResult<Map<String, Object>> getListReturnOrders(Integer pageIndex, Integer pageSize);
}