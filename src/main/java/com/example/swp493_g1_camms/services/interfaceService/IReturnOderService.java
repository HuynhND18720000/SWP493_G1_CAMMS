package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.payload.request.ReturnOrderDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface IReturnOderService {
    ResponseEntity<?> createReturnOrder(Long orderId, String orderCode, Long confirmBy, String description,
                                        List<ConsignmentProductDTO> consignmentProductDTOs);
    ServiceResult<Map<String, Object>> getListReturnOrders(Integer pageIndex, Integer pageSize, LocalDateTime dateFrom,
                                                           LocalDateTime dateTo, String orderCode);
}