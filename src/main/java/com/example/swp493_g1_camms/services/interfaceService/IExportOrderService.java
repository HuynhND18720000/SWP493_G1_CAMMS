package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.payload.request.ExportOrderRequest;
import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import com.example.swp493_g1_camms.payload.request.ProductForExport;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IExportOrderService {
    ResponseEntity<?> createExportOrder(ExportOrderRequest exportOrderRequest);

    ResponseEntity<?> getInfoProductInWareHouse(Long product_id, Integer pageIndex, Integer pageSize);
    ResponseEntity<?> getListExportOrder(Integer pageIndex, Integer pageSize);
}
