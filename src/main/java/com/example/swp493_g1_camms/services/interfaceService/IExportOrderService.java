package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ExportOrderRequest;
import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IExportOrderService {
    ResponseEntity<?> createExportOrder(ExportOrderRequest exportOrderRequest);

    ResponseEntity<?> getInfoProductInWareHouse(Long product_id, Integer pageIndex, Integer pageSize);

    ServiceResult<Map<String, Object>> getOderDetail(Long orderId);
}
