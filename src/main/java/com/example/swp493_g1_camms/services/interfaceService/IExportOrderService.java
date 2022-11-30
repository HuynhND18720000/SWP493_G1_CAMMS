package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.payload.request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IExportOrderService {
    ResponseEntity<?> createExportOrder(ExportOrderRequest exportOrderRequest);

    ResponseEntity<?> getInfoProductInWareHouse(Long product_id, Integer pageIndex, Integer pageSize);
    ResponseEntity<?> getListExportOrder(Integer pageIndex, Integer pageSize);

    ServiceResult<Map<String,Object>> getListExportOrders(Integer pageIndex, Integer pageSize, Integer status,
                                                          String dateFrom, String dateTo, Long userId, String orderCode);

    ServiceResult<Map<String,Object>> getExportOderDetail(Long orderId);

    ResponseEntity<?> confirmExportOrder(Long orderId, Long confirmBy);

    ResponseEntity<?> cancelExportOrder(Long orderId, Long confirmBy);

    ResponseEntity<?> editExportOrder(Long orderId, List<ConsignmentProductDTO> consignmentDTOList );
    ServiceResult<Map<String, Object>> getOderDetail(Long orderId);

    ResponseEntity<?> deliveredExportOrder(Long orderId);

    ResponseEntity<?> cancelDeliveredOrder(Long orderId, List<OrderStatusDeliverDTO> orderStatusDeliverDTOS);

    ServiceResult<Map<String, Object>> getDetailCancelDeliveredOrder(Long orderId);

    ResponseEntity<?> displayListProductInWarehouseToDropList();
}
