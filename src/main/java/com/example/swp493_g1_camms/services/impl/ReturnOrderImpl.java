package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.*;
import com.example.swp493_g1_camms.payload.request.ConsignmentRequest;
import com.example.swp493_g1_camms.payload.request.ProductRequest;
import com.example.swp493_g1_camms.payload.request.ReturnOrderDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.services.interfaceService.IReturnOderService;
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
public class ReturnOrderImpl implements IReturnOderService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    IOrderRepository orderRepository;
    @Autowired
    IStatusRepository statusRepository;
    @Autowired
    IOrderTypeRepository orderTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IConsignmentRepository consignmentRepository;

    @Autowired
    IOrderDetailRepository orderDetailRepository;

    @Autowired
    IConsignmentProductRepository iConsignmentProductRepository;
    @Autowired
    IWarehouseRepository iWarehouseRepository;

    @Override
    public ResponseEntity<?> createReturnOrder(ReturnOrderDTO returnOrderDTO) {
        Order order = new Order();
        ResponseVo responseVo = new ResponseVo();
        MessageResponse messageResponse = new MessageResponse();
        try{
            //update old order
            order = orderRepository.getById(returnOrderDTO.getOrderId());
            order.setStatus(statusRepository.findStatusById(Long.valueOf(4)));
            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
            order.setUpdateDate(ldt);
            orderRepository.save(order);

            //create order return
            Order order1 = new Order();
            order1.setOrderType(orderTypeRepository.getOrderTypeById(Long.valueOf(3)));
            order1.setOrderCode(returnOrderDTO.getOrderCode());
            Optional<User> user = userRepository.getUserById(returnOrderDTO.getConfirmBy());
            User u = user.get();
            if (u==null){
                responseVo.setMessage("User khong ton tai");
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
//            order1.setId(3L);
            order1.setUser(u);
            order1.setConfirmBy(returnOrderDTO.getConfirmBy());
            order1.setConfirmDate(ldt);
            order1.setCreatedDate(ldt);
            order1.setDescription(returnOrderDTO.getDescription());
            order1.setIsReturn(false);
            order1.setStatus(statusRepository.findStatusById(Long.valueOf(2)));
            order1.setManufacturer(null);
            order1.setDeletedAt(false);
            order1.setDescription("");
            orderRepository.save(order1);

            //Get return order id;
            Long orderId = order1.getId();
            System.out.println("dsa"+orderId);

            //Add to consignment product
            for(int i = 0 ; i < returnOrderDTO.getConsignmentProductDTOs().size(); i++){
                Consignment consignment = new Consignment();
                consignment.setImportDate(ldt);
                consignment.setDeletedAt(false);
                consignment.setWarehouse(iWarehouseRepository.getById(returnOrderDTO.getWarehouseId()));
                consignmentRepository.save(consignment);
                Long consignmentId = consignment.getId();

                //Save to orderDetail
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setConsignment(consignmentRepository.getById(consignmentId));
                orderDetail.setOrder(orderRepository.getById(orderId));
                orderDetailRepository.save(orderDetail);
                //Save to consigmentProduct
                ConsignmentProduct consignmentProduct = new ConsignmentProduct();
                consignmentProduct.setProduct(
                        productRepository.findProductById(returnOrderDTO.getConsignmentProductDTOs().get(i).getProductId()));
                consignmentProduct.setConsignment(consignmentRepository.getById(consignmentId));
                consignmentProduct.setQuantity(returnOrderDTO.getConsignmentProductDTOs().get(i).getQuantity());
                consignmentProduct.setUnitPrice(returnOrderDTO.getConsignmentProductDTOs().get(i).getUnitPrice());
                String str = returnOrderDTO.getConsignmentProductDTOs().get(i).getExpirationDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
                consignmentProduct.setExpirationDate(dateTime);
                consignmentProduct.setDeletedAt(false);
                consignmentProduct.setMark_get_product_from_consignment(returnOrderDTO.getConsignmentProductDTOs().get(i).getConsignmentId());
                iConsignmentProductRepository.save(consignmentProduct);
            }

            messageResponse.setMessage("Tao phieu tra hang thanh cong");
            return new ResponseEntity<>(messageResponse,HttpStatus.OK);
        }catch (Exception e){
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }

    @Override
    public ServiceResult<Map<String, Object>> getListReturnOrders(Integer pageIndex, Integer pageSize) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("id").descending());
        try {
            List<Map<String, Object>> orderList = orderRepository.getListReturnOrders(pageable);
            BigInteger totalRecord = BigInteger.valueOf(0);
            if (!orderList.isEmpty()) {
                totalRecord = (BigInteger) orderList.get(0).get("totalRecord");
            }
            output.put("orderReturnList", orderList);
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
}