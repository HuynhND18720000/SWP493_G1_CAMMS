package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.*;
import com.example.swp493_g1_camms.payload.request.ConsignmentRequest;
import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import com.example.swp493_g1_camms.payload.request.ProductRequest;
import com.example.swp493_g1_camms.payload.response.ListProductResponse;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.payload.response.ProductResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.services.interfaceService.IImportOrderService;
import com.example.swp493_g1_camms.utils.ConvertDateUtils;
import com.example.swp493_g1_camms.utils.ConvertToEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImportOrderImpl implements IImportOrderService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ConvertToEntities convertToEntities;
    @Autowired
    IOrderRepository orderRepository;
    @Autowired
    ConvertDateUtils convertDateUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IManufacturerRepository manufacturerRepository;
    @Autowired
    IOrderTypeRepository orderTypeRepository;
    @Autowired
    IStatusRepository statusRepository;
    @Autowired
    IWarehouseRepository warehouseRepository;
    @Autowired
    IConsignmentRepository consignmentRepository;
    @Autowired
    IRelationConsignmentProductRepository relationConsignmentProductRepository;

    ListProductResponse listProductResponse;
    @Override
    public ResponseEntity<?> createOrder(ImportOrderRequest importOrderRequest) {
        List<ProductRequest> productList = new ArrayList<>();
        List<Product> list = new ArrayList<>();
        Order order = new Order();
        ResponseVo responseVo = new ResponseVo();
        MessageResponse messageResponse = new MessageResponse();
        try{
            List<Order> lo = orderRepository.getLastOrderCode();
            Order o = lo.get(0);
            int new_orderCode = Integer.parseInt((o.getId()+"").trim()) + 1;
            order.setOrderCode(String.valueOf(new_orderCode));
            order.setCreatedDate(convertDateUtils.convertDateFormat());
            order.setIsReturn(false);
            order.setDeletedAt(false);
            Optional<User> user = userRepository.getUserById(importOrderRequest.getUser_Id());
            User u = user.get();
            if (u==null){
                responseVo.setMessage("User khong ton tai");
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }

            order.setUser(u);
            order.setManufacturer(manufacturerRepository.findManufactorById(importOrderRequest.getManufacturerId()));
            //lam tam cho nay
            order.setOrderType(orderTypeRepository.getOrderTypeById(Long.valueOf(1)));
            order.setStatus(statusRepository.findStatusById(Long.valueOf(1)));
            orderRepository.save(order);

            //them vao trong segment
            Warehouse warehouse = warehouseRepository.findWarehouseById(importOrderRequest.getWarehouseId());
            Consignment consignment = new Consignment();
            consignment.setWarehouse(warehouse);
            consignment.setImportDate(convertDateUtils.convertDateFormat());
            consignment.setDeletedAt(false);
            consignmentRepository.save(consignment);

            //them vao consignment product
            ConsignmentRequest consignmentRequest = importOrderRequest.getConsignmentRequest();
            ConsignmentProduct consignmentProduct = new ConsignmentProduct();
            productList = consignmentRequest.getProductRequestList();
            for (ProductRequest p: productList
                 ) {
                Product p1 = convertToEntities.convertProduct(p);
                list.add(p1);
            }
            for (Product pro: list
                 ) {
                consignmentProduct.setConsignment(consignment);
                consignmentProduct.setProduct(pro);
                consignmentProduct.setQuantity(pro.getQuantity());
                consignmentProduct.setUnitPrice(pro.getUnitprice());
                consignmentProduct.setDeletedAt(false);
                relationConsignmentProductRepository.save(consignmentProduct);
            }

            //them vao order detail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            double totalPrice = 0;
            for (Product pro: list
            ) {
                totalPrice += pro.getUnitprice();
            }
            orderDetail.setUnitPrice(totalPrice);
            orderDetail.setConsignment(consignment);

            messageResponse.setMessage("Tao phieu nhap hang thanh cong");
            return new ResponseEntity<>(messageResponse,HttpStatus.OK);
        }catch (Exception e){
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }

    @Override
    public ResponseEntity<?> getProductByManufacturer(Long id) {
        MessageResponse messageResponse = new MessageResponse();
        try{
            listProductResponse = new ListProductResponse();
            ResponseVo responseVo = new ResponseVo();
            List<Product> listProduct = productRepository.getAllProductByManufacturerId(id);
            ProductResponse productResponse = new ProductResponse();
            Map<String, Object> map = new HashMap<>();
            if (listProduct.size() == 0) {
                map.put("product", null);
                map.put("totalRecord", 0);
                responseVo.setMessage("Không tìm thấy List Manufacturer");
                responseVo.setData(map);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            map.put("product", listProductResponse.createSuccessData(listProduct));
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch(Exception e){
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }

    }
}
