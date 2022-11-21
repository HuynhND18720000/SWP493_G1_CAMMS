package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IExportOrderService {
    ServiceResult<Map<String,Object>> getListExportOrders(Integer pageIndex, Integer pageSize);

    ServiceResult<Map<String,Object>> getExportOderDetail(Integer pageIndex, Integer pageSize, Long orderId);

    ResponseEntity<?> confirmExportOrder(Long orderId, Long confirmBy);

    ResponseEntity<?> cancelExportOrder(Long orderId, Long confirmBy);

    ResponseEntity<?> editExportOrder(List<ConsignmentProductDTO> consignmentDTOList );
}
