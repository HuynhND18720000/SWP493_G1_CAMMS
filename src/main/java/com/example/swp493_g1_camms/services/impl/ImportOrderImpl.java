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

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    @Autowired
    IOrderDetailRepository orderDetailRepository;
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
            //LocalDateTime currentDate = convertDateUtils.convertDateFormat();

            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());

            consignment.setImportDate(ldt);

            consignment.setDeletedAt(false);
            Long consignment_code = consignmentRepository.getLastConsignmentCode()+1;
            consignment.setConsignment_code(consignment_code);
            consignmentRepository.save(consignment);

            // them vao consignment product
            Long currentConsignmentCode = consignment_code;
            Consignment consignment_id = consignmentRepository.getCurrentConsignmentId(currentConsignmentCode);

            ConsignmentRequest consignmentRequest = importOrderRequest.getConsignmentRequest();

            productList = consignmentRequest.getProductRequestList();
            for (ProductRequest p: productList
                 ) {
                Product p1 = convertToEntities.convertProductToAddConsignmentProduct(p);
                list.add(p1);
                System.out.println("list khi lay tu client la: "+p1);
            }


            for (Product pro: list
                 ) {
                ConsignmentProduct consignmentProduct = new ConsignmentProduct();
                consignmentProduct.setConsignment(consignment_id);
                consignmentProduct.setProduct(pro);
                consignmentProduct.setQuantity(pro.getQuantity());
                consignmentProduct.setUnitPrice(pro.getUnitprice());
                consignmentProduct.setDeletedAt(false);
                consignmentProduct.setExpirationDate(pro.getOutDate());
                relationConsignmentProductRepository.save(consignmentProduct);
            }

            //them vao order detail
            OrderDetail orderDetail = new OrderDetail();
            Order order_after_create = orderRepository.getOrderByOrderCode(order.getOrderCode());
            orderDetail.setOrder(order_after_create);
            double totalPrice = 0;
            for (Product pro: list
            ) {
                totalPrice += pro.getUnitprice();
            }
            Consignment consignment_add_ordeDetail = consignmentRepository.getConsignmentByConsignmentCode(
                    consignment_code
            );
            orderDetail.setConsignment(consignment_add_ordeDetail);
            orderDetailRepository.save(orderDetail);
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
