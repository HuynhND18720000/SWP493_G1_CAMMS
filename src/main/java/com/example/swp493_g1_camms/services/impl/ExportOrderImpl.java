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

    @Override
    public ServiceResult<Map<String, Object>> getListExportOrders(Integer pageIndex, Integer pageSize) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();
        Pageable pagable = PageRequest.of(pageIndex, pageSize,
                Sort.by("id").descending());

        try {
            List<Map<String, Object>> orderList = iExportOrderRepository.getListImportOrders(pagable);
            BigInteger totalRecord = BigInteger.valueOf(0);
            if (!orderList.isEmpty()) {
                totalRecord = (BigInteger) orderList.get(0).get("totalRecord");
            }
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
    public ServiceResult<Map<String, Object>> getExportOderDetail(Integer pageIndex, Integer pageSize, Long orderId) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();
        Pageable pagable = PageRequest.of(pageIndex, pageSize);
        try {
            List<Map<String, Object>> listImportProducts = iExportOrderRepository.getExportOrderDetail(orderId, pagable);
            BigInteger totalRecord = BigInteger.valueOf(0);
            if (!listImportProducts.isEmpty()) {
                totalRecord = (BigInteger) listImportProducts.get(0).get("totalRecord");
            }
            output.put("listExportProduct", listImportProducts);
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
    public ResponseEntity<?> editExportOrder(List<ConsignmentProductDTO> consignmentProductDTOList) {
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
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Sửa thông tin xuất hàng thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
}
