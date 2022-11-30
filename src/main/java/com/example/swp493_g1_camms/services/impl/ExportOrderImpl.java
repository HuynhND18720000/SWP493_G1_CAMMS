package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Order;
import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.entities.Status;
import com.example.swp493_g1_camms.entities.User;
import com.example.swp493_g1_camms.payload.request.ExportOrderRequest;
import com.example.swp493_g1_camms.payload.response.ExportOrderResponse;
import com.example.swp493_g1_camms.payload.response.ListConsignmentProductResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.services.interfaceService.IExportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExportOrderImpl implements IExportOrderService {
    @Autowired
    IExportOrderRepository exportOrderRepository;

    @Autowired
    IWarehouseRepository warehouseRepository;
    @Autowired
    private IExportOrderRepository iExportOrderRepository;

    @Override
    public ResponseEntity<?> createExportOrder(ExportOrderRequest exportOrderRequest) {
        Order order = new Order();
        User user;
        Status status = new Status();
        ResponseVo responseVo = new ResponseVo();
        try{

        }catch(Exception e){

        }

       return null;
    }

    @Override
    public ResponseEntity<?> getInfoProductInWareHouse(Long product_id,Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        List<Map<String, Object>> listProduct = exportOrderRepository.getProductInWareHouse(product_id);
        List<ExportOrderResponse> exportOrderResponseList =  new ArrayList<>();
        List<ListConsignmentProductResponse> consignmentProductResponseList = new ArrayList<>();
        ExportOrderResponse exportOrderResponse = new ExportOrderResponse();
        ResponseVo responseVo = new ResponseVo();
        int total_Product_In_Consignment = 0;

        for (Map<String, Object> lp: listProduct
             ) {
            exportOrderResponse.setProductId(Long.parseLong(lp.get("productId").toString()));
            exportOrderResponse.setProductName(lp.get("productName").toString());
            exportOrderResponse.setProductCode(lp.get("productCode").toString());
            exportOrderResponse.setQuantity(0);
            exportOrderResponse.setUnitPrice((Double) lp.get("unitPrice"));
            exportOrderResponse.setUnitMeasure(lp.get("unitMeasure").toString());
            exportOrderResponseList.add(exportOrderResponse);
            //can nhac co de them cot thanh tien hay ko
            //so luong record product
            total_Product_In_Consignment += 1;

            //hien thi list lo hang nhap san pham cos id la productId
            ListConsignmentProductResponse consignmentProductResponse = new ListConsignmentProductResponse();
            consignmentProductResponse.setId(Long.parseLong(lp.get("consignment_id").toString()));
            consignmentProductResponse.setWarehouseId(Long.parseLong(lp.get("wareHouseId").toString()));
            consignmentProductResponse.setWarehouseName(lp.get("warehouseName").toString());
            consignmentProductResponse.setImportDate(lp.get("importDate").toString());
            consignmentProductResponse.setExpirationDate(lp.get("expirationDate").toString());
            consignmentProductResponse.setQuantityInstock((Integer) lp.get("quantityInstock"));
            consignmentProductResponse.setQuantity(0);
            consignmentProductResponseList.add(consignmentProductResponse);
            exportOrderResponse.setConsignmentList(consignmentProductResponseList);
        }
        map.put("listConsignmentProduct",exportOrderResponse.getConsignmentList());
        map.put("listProduct",exportOrderResponse);
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        map.put("totalRecord",total_Product_In_Consignment);
        map.put("status",200);
        responseVo.setData(map);
        responseVo.setMessage("lay list product va consignment cuar product ve thanh cong");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ServiceResult<Map<String, Object>> getOderDetail(Long orderId) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();
        try {
            List<Map<String, Object>> listImportProducts = iExportOrderRepository.getReturnOrderDetail(orderId);
            output.put("listExportProduct", listImportProducts);
            mapServiceResult.setData(output);
            mapServiceResult.setMessage("success");
            mapServiceResult.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            mapServiceResult.setMessage("fail");
            mapServiceResult.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return mapServiceResult;
        }
        return mapServiceResult;
    }

}
