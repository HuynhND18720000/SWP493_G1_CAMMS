package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.*;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.payload.request.ConsignmentRequest;
import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import com.example.swp493_g1_camms.payload.request.ProductRequest;
import com.example.swp493_g1_camms.payload.response.ListProductResponse;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.services.interfaceService.IImportOrderService;
import com.example.swp493_g1_camms.utils.Constant;
import com.example.swp493_g1_camms.utils.ConvertDateUtils;
import com.example.swp493_g1_camms.utils.ConvertToEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ImportOrderServiceImpl implements IImportOrderService {

    @Autowired
    IProductRepository productRepository;
    @Autowired
    ConvertToEntities convertToEntities;
    @Autowired
    IOrderRepository orderRepository;
    @Autowired
    ConvertDateUtils convertDateUtils;
    @Autowired
    IUserRepository userRepository;
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
    @Autowired
    IImportOrderRepository importOrderRepository;
    ListProductResponse listProductResponse;
    @Autowired
    IConsignmentProductRepository consignmentProductRepository;

    @Override
    public ResponseEntity<?> createOrder(ImportOrderRequest importOrderRequest) {
        List<ProductRequest> productList = new ArrayList<>();
        List<Product> list = new ArrayList<>();
        Order order = new Order();
        ResponseVo responseVo = new ResponseVo();
        MessageResponse messageResponse = new MessageResponse();
        try{
            List<Order> lo = orderRepository.getLastOrderCode();
            //kt list rong hay ko
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
            //danh sach time nhap hang cua tung san pham
            List<LocalDateTime> list_import_date_of_product = new ArrayList<>();

            for (ProductRequest p: productList
                 ) {
                Product p1 = convertToEntities.convertProductToAddConsignmentProduct(p);
                list.add(p1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime dateTime = LocalDateTime.parse(p.getImport_date(), formatter);
                list_import_date_of_product.add(dateTime);
                System.out.println("list khi lay tu client la: "+p1);
            }
            int count = 0;
            for (Product pro: list
                ) {
                    ConsignmentProduct consignmentProduct = new ConsignmentProduct();
                    consignmentProduct.setConsignment(consignment_id);
                    consignmentProduct.setProduct(pro);
                    consignmentProduct.setQuantity(pro.getQuantity());
                    consignmentProduct.setUnitPrice(pro.getUnitprice());
                    consignmentProduct.setDeletedAt(false);
                    consignmentProduct.setQuantity_sale(pro.getQuantity());
                    consignmentProduct.setExpirationDate(pro.getOutDate());
                    consignmentProduct.setImport_date(
                            list_import_date_of_product.
                                    get(count));
                    count++;
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
            System.out.println("loi khong tao dc");
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

    @Override
    public ServiceResult<Map<String, Object>> getListImportOrders(Integer pageIndex, Integer pageSize,  Integer status,
                                                                  LocalDateTime dateFrom, LocalDateTime dateTo, Long userId, String orderCode) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by("id").descending());

        try {
            List<Map<String, Object>> orderList = importOrderRepository.getListImportOrders(status, dateFrom, dateTo, userId, orderCode, pageable);
            BigInteger totalRecord = orderRepository.getTotalImportRecord(status, dateFrom, dateTo, userId, orderCode);
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
    public ServiceResult<Map<String, Object>> getImportOderDetail(Long orderId) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();

        try {
            List<Map<String, Object>> listImportProducts = importOrderRepository.getImportOrderDetail(orderId);

            output.put("listImportProduct", listImportProducts);

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
    public ResponseEntity<?> confirmOrder(Long orderId, Long confirmBy) {
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
            Order order = importOrderRepository.getOrderById(orderId);
            if(order.getStatus().getId() == 1L) {
                order.setConfirmBy(confirmBy);
                order.setStatus(status);
                importOrderRepository.save(order);
                List<ConsignmentProduct> consignmentProducts =
                        consignmentProductRepository.getConsignmentProductByOrderId(orderId);
                for (int i = 0; i < consignmentProducts.size(); i++) {
                    ConsignmentProductKey consignmentProductKey = consignmentProducts.get(i).getId();
                    Product product = productRepository.findProductById(consignmentProductKey.getProductid());
                    product.setQuantity(product.getQuantity() + consignmentProducts.get(i).getQuantity());

                    if (product.getId() == consignmentProductKey.getProductid()) {
                        double totalPrice = 0;
                        Long totalQuantity = 0L;
                        List<ConsignmentProduct> consignmentProducts1 =
                                consignmentProductRepository.findAllConsignmentProductForAveragePrice(product.getId());
                        for (int j = 0; j < consignmentProducts1.size(); j++) {
                            totalPrice = totalPrice +
                                    consignmentProducts1.get(j).getUnitPrice() * consignmentProducts1.get(j).getQuantity_sale();
                            totalQuantity = totalQuantity + consignmentProducts1.get(j).getQuantity_sale();
                        }
                        double avaragePrice = totalPrice / totalQuantity;
                        ConsignmentProduct consignmentProduct =
                                consignmentProductRepository.getConsignmentProductById(consignmentProductKey.getConsignmentid(),
                                        consignmentProductKey.getProductid());
                        consignmentProduct.setAverage_price(avaragePrice);
                        consignmentProductRepository.save(consignmentProduct);
                        product.setLastAveragePrice(avaragePrice);
                    }

                    productRepository.save(product);
                }
                ResponseVo responseVo = new ResponseVo();
                responseVo.setMessage("Xác nhận nhập hàng thành công !!");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }else{
                throw new Exception();
            }
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
    public ResponseEntity<?> cancelOrder(Long orderId, Long confirmBy) {
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
            Order order = importOrderRepository.getOrderById(orderId);
            if(order.getStatus().getId() == 1L) {
                order.setConfirmBy(confirmBy);
                order.setStatus(status);
                importOrderRepository.save(order);
                ResponseVo responseVo = new ResponseVo();
                responseVo.setMessage("Hủy xác nhận nhập hàng thành công !!");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }else{
                throw new Exception();
            }
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
    public ResponseEntity<?> editOrder(Long orderId, List<ConsignmentProductDTO> consignmentProductDTOList) {
        for (ConsignmentProductDTO cPDTO1: consignmentProductDTOList) {
            ConsignmentProduct consignmentProduct =
                    consignmentProductRepository.getConsignmentProductById(cPDTO1.getConsignmentId(), cPDTO1.getProductId());
            String str = cPDTO1.getExpirationDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
            consignmentProduct.setExpirationDate(dateTime);
            consignmentProduct.setQuantity(cPDTO1.getQuantity());
            consignmentProduct.setUnitPrice(cPDTO1.getUnitPrice());
            consignmentProductRepository.save(consignmentProduct);
        }
        Order order = new Order();
        order = orderRepository.getById(orderId);
        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        order.setUpdateDate(ldt);
        orderRepository.save(order);
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Sửa thông tin nhập hàng thành công !!");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
}
