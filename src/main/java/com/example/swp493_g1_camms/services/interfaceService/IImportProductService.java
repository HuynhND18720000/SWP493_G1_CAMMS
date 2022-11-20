package com.example.swp493_g1_camms.services.interfaceService;

import com.example.swp493_g1_camms.entities.ServiceResult;

import java.util.Map;

public interface IImportProductService {
    ServiceResult<Map<String,Object>> getListImportOrders(Integer pageIndex, Integer pageSize);

    ServiceResult<Map<String,Object>> getImportOderDetail(Integer orderId);
}
