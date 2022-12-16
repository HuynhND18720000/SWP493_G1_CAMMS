//package com.example.swp493_g1_camms.services;
//
//import com.example.swp493_g1_camms.controller.ImportOrderController;
//import com.example.swp493_g1_camms.entities.*;
//import com.example.swp493_g1_camms.payload.request.ConsignmentRequest;
//import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
//import com.example.swp493_g1_camms.payload.request.ProductRequest;
//import com.example.swp493_g1_camms.payload.response.MessageResponse;
//import com.example.swp493_g1_camms.repository.*;
//import com.example.swp493_g1_camms.services.interfaceService.IImportOrderService;
//import com.example.swp493_g1_camms.utils.Constant;
//import com.example.swp493_g1_camms.utils.ConvertDateUtils;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//public class ImportOrderTestService {
//    @Mock
//    @Autowired
//    IOrderRepository orderRepository;
//    @Mock
//    @Autowired
//    ConvertDateUtils convertDateUtils;
//    @Mock
//    @Autowired
//    IOrderTypeRepository orderTypeRepository;
//    @Mock
//    @Autowired
//    IStatusRepository statusRepository;
//    @Mock
//    @Autowired
//    IConsignmentRepository consignmentRepository;
//    @Mock
//    @Autowired
//    IImportOrderService importOrderService;
//    @BeforeAll
//    static void setUpBeforeClass() throws Exception {
//    }
//
//    @Test
//    void testCreateImportOrder() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String date = "2000-07-18";
//        //convert String to LocalDate
//        LocalDate localDate = LocalDate.parse(date, formatter);
//        User user = new User(1L,"admin",
//                "$2a$10$JUDtWfCCwtjnQwh5YtMOgOYvu89ALLPbUkjTfz1O0KED6LzJaswV6",
//                "nguyen dinh huynh",
//                true,"huynhndhe141044@fpt.edu.vn",
//                "0348745668","yummy.img",localDate);
//
//        Warehouse warehouse = new Warehouse(1L,"hoa lac",false,"hoa lac");
//        Manufacturer manufacturer = new Manufacturer(1L,
//                "Công Ty cổ phần tập đoàn giống cây trồng Việt Nam – chi nhánh Hà Nam VINASEED",
//                "Hà Nam","012345678","abc@gmail.com"
//                );
//
//        Order order = new Order();
//        order.setOrderCode(String.valueOf(130));
//        order.setCreatedDate(convertDateUtils.convertDateFormat());
//        order.setIsReturn(false);
//        order.setDeletedAt(false);
//        order.setUser(user);
//        order.setManufacturer(manufacturer);
//        order.setOrderType(orderTypeRepository.getOrderTypeById(Long.valueOf(1)));
//        order.setStatus(statusRepository.findStatusById(Long.valueOf(1)));
//
//        Consignment consignment = new Consignment();
//        consignment.setWarehouse(warehouse);
//        Date in = new Date();
//        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
//        consignment.setImportDate(ldt);
//        consignment.setDeletedAt(false);
//        Long consignment_code = consignmentRepository.getLastConsignmentCode()+1;
//        consignment.setConsignment_code(consignment_code);
//
//        ImportOrderRequest importOrderRequest = new ImportOrderRequest();
//        importOrderRequest.setUser_Id(user.getId());
//        importOrderRequest.setWarehouseId(warehouse.getId());
//        importOrderRequest.setManufacturerId(manufacturer.getId());
//
//        ConsignmentRequest consignmentRequest = new ConsignmentRequest();
//        consignmentRequest.setImportDate("2022-11-22 00:00:00");
//
//        List<ProductRequest> productRequestList = new ArrayList<>();
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setImport_date("2022-11-22 00:00:00");
//        productRequest.setId(1L);
//        productRequest.setQuantity(900);
//        productRequest.setUnit_price(1000.0);
//        productRequest.setExpiration_date("2022-12-22 00:00:00");
//        productRequestList.add(productRequest);
//
//        consignmentRequest.setProductRequestList(productRequestList);
//        importOrderRequest.setConsignmentRequest(consignmentRequest);
//
//        when(orderRepository.save(order)).thenReturn(order);
//        when(consignmentRepository.save(consignment)).thenReturn(consignment);
//
//        //expected
//        MessageResponse expected = new MessageResponse();
//        expected.setMessage("Tạo phiếu nhập kho thành công !");
//        expected.setStatus(Constant.SUCCESS);
//        //actual
//        ResponseEntity<?> responseEntity = importOrderService.createOrder(importOrderRequest);
//        MessageResponse actual = (MessageResponse) responseEntity.getBody();
//
//        //call importGoodsService
//        ResponseEntity ResponseEntity = new ResponseEntity(expected, HttpStatus.OK);
//        when(importOrderService.createOrder(importOrderRequest)).thenReturn(ResponseEntity);
//
//        assertEquals(expected.getMessage(), actual.getMessage());
//        assertEquals(expected.getStatus(), actual.getStatus());
//    }
//
//    @Test
//    void testCreateImportOrderInputNull() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String date = null;
//        //convert String to LocalDate
//        LocalDate localDate = null;
//        User user = null;
//
//        Warehouse warehouse = null;
//        Manufacturer manufacturer = null;
//
//        List<Order> lo = orderRepository.getLastOrderCode();
//        Order o = lo.get(0);
//        int new_orderCode = Integer.parseInt((o.getId()+"").trim()) + 1;
//        Order order = new Order();
//        order.setOrderCode(String.valueOf(new_orderCode));
//        order.setCreatedDate(convertDateUtils.convertDateFormat());
//        order.setIsReturn(false);
//        order.setDeletedAt(false);
//        order.setUser(user);
//        order.setManufacturer(manufacturer);
//        order.setOrderType(orderTypeRepository.getOrderTypeById(Long.valueOf(1)));
//        order.setStatus(statusRepository.findStatusById(Long.valueOf(1)));
//
//        Consignment consignment = new Consignment();
//        consignment.setWarehouse(warehouse);
//        Date in = new Date();
//        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
//        consignment.setImportDate(ldt);
//        consignment.setDeletedAt(false);
//        Long consignment_code = consignmentRepository.getLastConsignmentCode()+1;
//        consignment.setConsignment_code(consignment_code);
//
//        ImportOrderRequest importOrderRequest = new ImportOrderRequest();
//        importOrderRequest.setUser_Id(user.getId());
//        importOrderRequest.setWarehouseId(warehouse.getId());
//        importOrderRequest.setManufacturerId(manufacturer.getId());
//
//        ConsignmentRequest consignmentRequest = new ConsignmentRequest();
//        consignmentRequest.setImportDate("2022-11-22 00:00:00");
//
//        List<ProductRequest> productRequestList = new ArrayList<>();
//        ProductRequest productRequest = new ProductRequest();
//        productRequest.setImport_date("2022-11-22 00:00:00");
//        productRequest.setId(1L);
//        productRequest.setQuantity(900);
//        productRequest.setUnit_price(1000.0);
//        productRequest.setExpiration_date("2022-12-22 00:00:00");
//        productRequestList.add(productRequest);
//
//        consignmentRequest.setProductRequestList(productRequestList);
//        importOrderRequest.setConsignmentRequest(consignmentRequest);
//
//        when(orderRepository.save(order)).thenReturn(order);
//        when(consignmentRepository.save(consignment)).thenReturn(consignment);
//
//        //expected
//        MessageResponse expected = new MessageResponse();
//        expected.setMessage("Tạo phiếu nhập kho that bai !");
//        expected.setStatus(Constant.FAIL);
//        //actual
//        ResponseEntity<?> responseEntity = importOrderService.createOrder(null);
//        MessageResponse actual = (MessageResponse) responseEntity.getBody();
//
//        //call importGoodsService
//        ResponseEntity ResponseEntity = new ResponseEntity(expected, HttpStatus.BAD_REQUEST);
//        when(importOrderService.createOrder(importOrderRequest)).thenReturn(ResponseEntity);
//
//        assertEquals(expected.getMessage(), actual.getMessage());
//        assertEquals(expected.getStatus(), actual.getStatus());
//    }
//}
