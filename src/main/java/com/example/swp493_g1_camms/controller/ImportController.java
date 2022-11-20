package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.services.interfaceService.IImportProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Map;

@RequestMapping("/api/import")
@RestController
public class ImportController {

    private final int defaultPage = 1;

    private final int defaultSize = 5;
    @Autowired
    private IImportProductService iImportProductService;

    @GetMapping("/list")
    public ResponseEntity<ServiceResult<Map<String, Object>>> listImportGoods(@RequestParam(required = false) Integer pageIndex,

                                                                              @RequestParam(required = false) Integer pageSize) throws ParseException {
        pageIndex = pageIndex == null ? defaultPage : pageIndex;
        pageSize = pageSize == null ? defaultSize : pageSize;
        try {
            pageIndex = pageIndex - 1;
            ServiceResult<Map<String, Object>> mapServiceResult = iImportProductService.getListImportOrders(pageIndex, pageSize);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getOrderDetail")
    public ResponseEntity<ServiceResult<Map<String, Object>>> getOrderDetail(Integer orderId) throws ParseException {

        try {
            ServiceResult<Map<String, Object>> mapServiceResult = iImportProductService.getImportOderDetail(orderId);
            return ResponseEntity.ok(mapServiceResult);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @GetMapping("/confirmOders")
//    public ResponseEntity<ServiceResult<Map<String, Object>>> confirmOrder(Integer orderId) throws ParseException {
//
//        try {
//            pageIndex = pageIndex - 1;
//            ServiceResult<Map<String, Object>> mapServiceResult = iImportProductService.getListImportOrders(pageIndex, pageSize);
//            return ResponseEntity.ok(mapServiceResult);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }


}
