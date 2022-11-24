package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IImportOrderService {
    ResponseEntity<?> createOrder(ImportOrderRequest importOrderRequest);
    ResponseEntity<?> getProductByManufacturer(Long id);

    ServiceResult<Map<String,Object>> getListImportOrders(Integer pageIndex, Integer pageSize,  Integer status,
                                                          String dateFrom, String dateTo, Long userId, String orderCode);

    ServiceResult<Map<String,Object>> getImportOderDetail(Integer pageIndex, Integer pageSize, Long orderId);

    ResponseEntity<?> confirmOrder(Long orderId, Long confirmBy);

    ResponseEntity<?> cancelOrder(Long orderId, Long confirmBy);

    ResponseEntity<?> editOrder(List<ConsignmentProductDTO> consignmentDTOList );
}
