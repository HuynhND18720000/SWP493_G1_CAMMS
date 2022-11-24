package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.*;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.services.interfaceService.IExportOrderService;
import com.example.swp493_g1_camms.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class ExportOrderImpl implements IExportOrderService {

    @Autowired
    private IExportOrderRepository iExportOrderRepository;

    @Autowired
    private IConsignmentProductRepository iConsignmentProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IOrderRepository iOrderRepository;

    @Override
    public ServiceResult<Map<String, Object>> getListExportOrders(Integer pageIndex, Integer pageSize,  Integer status,
                                                                  String dateFrom, String dateTo, Long userId, String orderCode) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();
        Pageable pageable = PageRequest.of(pageIndex, pageSize,
                Sort.by("id").descending());
        LocalDateTime dateFrom1 = null;
        LocalDateTime dateTo1 = null;
        if(dateFrom == null || dateFrom.equalsIgnoreCase("")){
            dateFrom1 = null;
        }else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateFrom, formatter);
            dateFrom1 = dateTime;
        }
        if(dateTo == null || dateTo.equalsIgnoreCase("")){
            dateTo1 = null;
        }else {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime1 = LocalDateTime.parse(dateTo, formatter1);
            dateTo1 = dateTime1;
        }

        if(orderCode == null || orderCode.equalsIgnoreCase("") ){
            orderCode = "";
        }

        try {
            List<Map<String, Object>> orderList =
                    iExportOrderRepository.getListExportOrders(status, dateFrom1, dateTo1, userId, orderCode, pageable);
            BigInteger totalRecord = iOrderRepository.getTotalExportRecord(status, dateFrom1, dateTo1, userId, orderCode);
            output.put("orderList", orderList);
            output.put("pageIndex", pageIndex);
            output.put("pageSize", pageSize);
            output.put("totalRecord", totalRecord);
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

    @Override
    public ServiceResult<Map<String, Object>> getExportOderDetail(Long orderId) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();
        try {
            List<Map<String, Object>> listImportProducts = iExportOrderRepository.getExportOrderDetail(orderId);
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

    @Override
    public ResponseEntity<?> confirmExportOrder(Long orderId, Long confirmBy) {
        MessageResponse messageResponse = new MessageResponse();
        if (confirmBy == null || confirmBy.equals("")) {
            messageResponse.setMessage("Người xác nhận không thể bỏ trống !");
            messageResponse.setStatus(Constant.FAIL);
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        if (orderId == null || orderId.equals("")) {
            messageResponse.setMessage("Đơn hàng không tồn tại !");
            messageResponse.setStatus(Constant.FAIL);
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        Status status = new Status();
        status.setId(Constant.COMPLETED);
        try {
            Order order = iExportOrderRepository.getOrderById(orderId);
            order.setConfirmBy(confirmBy);
            order.setStatus(status);
            iExportOrderRepository.save(order);
            List<ConsignmentProduct> consignmentProducts =
                    iConsignmentProductRepository.getConsignmentProductByOrderId(orderId);
            for(int i =0 ; i < consignmentProducts.size(); i++){
                ConsignmentProductKey consignmentProductId = consignmentProducts.get(i).getId();
                Product product = productRepository.findProductById(consignmentProductId.getProductid());
                product.setQuantity(product.getQuantity() - consignmentProducts.get(i).getQuantity());
                productRepository.save(product);
            }
            ResponseVo responseVo = new ResponseVo();
            responseVo.setMessage("Xác nhận xuất hàng thành công !!");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            messageResponse.setMessage("Xác nhận đơn hàng thất bại !");
            messageResponse.setStatus(Constant.FAIL);
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }

    @Override
    public ResponseEntity<?> cancelExportOrder(Long orderId, Long confirmBy) {
        MessageResponse messageResponse = new MessageResponse();
        if (confirmBy == null || confirmBy.equals("")) {
            messageResponse.setMessage("Người xác nhận không thể bỏ trống !");
            messageResponse.setStatus(Constant.FAIL);
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        if (orderId == null || orderId.equals("")) {
            messageResponse.setMessage("Đơn hàng không tồn tại !");
            messageResponse.setStatus(Constant.FAIL);
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        Status status = new Status();
        status.setId(Constant.CANCEL);
        try {
            Order order = iExportOrderRepository.getOrderById(orderId);
            order.setConfirmBy(confirmBy);
            order.setStatus(status);
            iExportOrderRepository.save(order);
            ResponseVo responseVo = new ResponseVo();
            responseVo.setMessage("Hủy xác nhận xuất hàng thành công !!");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            messageResponse.setMessage("Xác nhận đơn hàng thất bại !");
            messageResponse.setStatus(Constant.FAIL);
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }

    @Override
    public ResponseEntity<?> editExportOrder(Long orderId, List<ConsignmentProductDTO> consignmentProductDTOList) {
        for (ConsignmentProductDTO cPDTO1: consignmentProductDTOList) {
            ConsignmentProduct consignmentProduct =
                    iConsignmentProductRepository.getConsignmentProductById(cPDTO1.getConsignmentId(), cPDTO1.getProductId());
            String str = cPDTO1.getExpirationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            consignmentProduct.setExpirationDate(dateTime);
            consignmentProduct.setQuantity(cPDTO1.getQuantity());
            consignmentProduct.setUnitPrice(cPDTO1.getUnitPrice());
            iConsignmentProductRepository.save(consignmentProduct);
        }
        Order order = new Order();
        order = iOrderRepository.getById(orderId);
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        order.setUpdateDate(ldt);
        iOrderRepository.save(order);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Sửa thông tin xuất hàng thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
}
