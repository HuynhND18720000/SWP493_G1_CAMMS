package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.entities.User;
import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.IConsignmentProductRepository;
import com.example.swp493_g1_camms.repository.IImportOrderRepository;
import com.example.swp493_g1_camms.repository.IProductRepository;
import com.example.swp493_g1_camms.repository.IUserRepository;
import com.example.swp493_g1_camms.services.interfaceService.IImportOrderService;
import com.example.swp493_g1_camms.utils.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ImportOrderTest {

    @Autowired
    IImportOrderRepository importOrderRepository;

    @Autowired
    IImportOrderService importOrder;

    @Autowired
    IProductRepository productRepository;

    @Autowired
    IConsignmentProductRepository consignmentProductRepository;

    @Autowired
    IImportOrderService importOrderService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ImportOrderController importOrderController;

    @Test
    void getListImportOrdersWithNoParameter() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Map<String,Object> map3= new HashMap<>();
        double totalPrice3 = 4.8E8;
        map3.put("total_price" , totalPrice3);
        map3.put("status" , "completed");
        map3.put("order_type_name" , "IMPORT");
        map3.put("user_name" , "admin");
        map3.put("manufacturer_name" , "Công ty CP Supe Phốt phát & Hóa chất Lâm Thao");
        map3.put("is_return" , null);
        map3.put("description" , null);
        Timestamp createdDate3 = Timestamp.valueOf("2021-12-16 01:34:15.842000");
        map3.put("update_date" ,null);
        map3.put("confirm_date" , null);
        map3.put("created_date" , createdDate3);
        map3.put("confirm_by_name" , "admin");
        map3.put("order_code" , "48");
        BigInteger id3 = BigInteger.valueOf(48);
        map3.put("id" , id3);
        orderList1.add(map3);

        Map<String,Object> map2= new HashMap<>();
        double totalPrice2 = 1.02E7;
        map2.put("total_price" , totalPrice2);
        map2.put("status" , "completed");
        map2.put("order_type_name" , "IMPORT");
        map2.put("user_name" , "admin");
        map2.put("manufacturer_name" , "Công Ty cổ phần tập đoàn giống cây trồng Việt Nam – chi nhánh Hà Nam VINASEED");
        map2.put("is_return" , null);
        map2.put("description" , null);
        Timestamp createdDate = Timestamp.valueOf("2022-11-06 18:34:07.000000");
        map2.put("update_date" , null);
        map2.put("confirm_date" , null);
        map2.put("created_date" , createdDate);
        map2.put("confirm_by_name" , "admin");
        map2.put("order_code" , "1");
        BigInteger id2 = BigInteger.valueOf(1);
        map2.put("id" , id2);
        orderList1.add(map2);
        Integer status = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        Long userID = null;
        String orderCode = "";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }


    @Test
    void getListImportOrdersWithPageIndexAndPageSize() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Map<String,Object> map3= new HashMap<>();
        double totalPrice3 = 4.8E8;
        map3.put("total_price" , totalPrice3);
        map3.put("status" , "completed");
        map3.put("order_type_name" , "IMPORT");
        map3.put("user_name" , "admin");
        map3.put("manufacturer_name" , "Công ty CP Supe Phốt phát & Hóa chất Lâm Thao");
        map3.put("is_return" , null);
        map3.put("description" , null);
        Timestamp createdDate3 = Timestamp.valueOf("2021-12-16 01:34:15.842000");
        map3.put("update_date" ,null);
        map3.put("confirm_date" , null);
        map3.put("created_date" , createdDate3);
        map3.put("confirm_by_name" , "admin");
        map3.put("order_code" , "48");
        BigInteger id3 = BigInteger.valueOf(48);
        map3.put("id" , id3);
        orderList1.add(map3);

        Map<String,Object> map2= new HashMap<>();
        double totalPrice2 = 1.02E7;
        map2.put("total_price" , totalPrice2);
        map2.put("status" , "completed");
        map2.put("order_type_name" , "IMPORT");
        map2.put("user_name" , "admin");
        map2.put("manufacturer_name" , "Công Ty cổ phần tập đoàn giống cây trồng Việt Nam – chi nhánh Hà Nam VINASEED");
        map2.put("is_return" , null);
        map2.put("description" , null);
        Timestamp createdDate = Timestamp.valueOf("2022-11-06 18:34:07.000000");
        map2.put("update_date" , null);
        map2.put("confirm_date" , null);
        map2.put("created_date" , createdDate);
        map2.put("confirm_by_name" , "admin");
        map2.put("order_code" , "1");
        BigInteger id2 = BigInteger.valueOf(1);
        map2.put("id" , id2);
        orderList1.add(map2);
        Integer status = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        Long userID = null;
        String orderCode = "";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithDateFrom() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Map<String,Object> map2= new HashMap<>();
        double totalPrice2 = 1.02E7;
        map2.put("total_price" , totalPrice2);
        map2.put("status" , "completed");
        map2.put("order_type_name" , "IMPORT");
        map2.put("user_name" , "admin");
        map2.put("manufacturer_name" , "Công Ty cổ phần tập đoàn giống cây trồng Việt Nam – chi nhánh Hà Nam VINASEED");
        map2.put("is_return" , null);
        map2.put("description" , null);
        Timestamp createdDate = Timestamp.valueOf("2022-11-06 18:34:07.000000");
        map2.put("update_date" , null);
        map2.put("confirm_date" , null);
        map2.put("created_date" , createdDate);
        map2.put("confirm_by_name" , "admin");
        map2.put("order_code" , "1");
        BigInteger id2 = BigInteger.valueOf(1);
        map2.put("id" , id2);
        orderList1.add(map2);
        Integer status = null;

        LocalDateTime dateFrom = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-11-06 00:00:00", formatter);
        dateFrom = dateTime;
        LocalDateTime dateTo = null;
//        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-01 09:46:04", formatter1);
//        dateTo = dateTime1;

        Long userID = null;
        String orderCode = "";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithDateTo() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Map<String,Object> map3= new HashMap<>();
        double totalPrice3 = 4.8E8;
        map3.put("total_price" , totalPrice3);
        map3.put("status" , "completed");
        map3.put("order_type_name" , "IMPORT");
        map3.put("user_name" , "admin");
        map3.put("manufacturer_name" , "Công ty CP Supe Phốt phát & Hóa chất Lâm Thao");
        map3.put("is_return" , null);
        map3.put("description" , null);
        Timestamp createdDate3 = Timestamp.valueOf("2021-12-16 01:34:15.842000");
        map3.put("update_date" ,null);
        map3.put("confirm_date" , null);
        map3.put("created_date" , createdDate3);
        map3.put("confirm_by_name" , "admin");
        map3.put("order_code" , "48");
        BigInteger id3 = BigInteger.valueOf(48);
        map3.put("id" , id3);
        orderList1.add(map3);


        Map<String,Object> map2= new HashMap<>();
        double totalPrice2 = 1.02E7;
        map2.put("total_price" , totalPrice2);
        map2.put("status" , "completed");
        map2.put("order_type_name" , "IMPORT");
        map2.put("user_name" , "admin");
        map2.put("manufacturer_name" , "Công Ty cổ phần tập đoàn giống cây trồng Việt Nam – chi nhánh Hà Nam VINASEED");
        map2.put("is_return" , null);
        map2.put("description" , null);
        Timestamp createdDate = Timestamp.valueOf("2022-11-06 18:34:07.000000");
        map2.put("update_date" , null);
        map2.put("confirm_date" , null);
        map2.put("created_date" , createdDate);
        map2.put("confirm_by_name" , "admin");
        map2.put("order_code" , "1");
        BigInteger id2 = BigInteger.valueOf(1);
        map2.put("id" , id2);
        orderList1.add(map2);
        Integer status = null;

        LocalDateTime dateFrom = null;
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dateTime = LocalDateTime.parse("2022-11-06 00:00:00", formatter);
//        dateFrom = dateTime;
        LocalDateTime dateTo = null;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-16 00:00:00", formatter1);
        dateTo = dateTime1;

        Long userID = null;
        String orderCode = "";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithOrderCode() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Integer status = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        Long userID = null;
        String orderCode = "2";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithDateFromAndDateTo() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);
        Map<String,Object> map2= new HashMap<>();
        double totalPrice2 = 1.02E7;
        map2.put("total_price" , totalPrice2);
        map2.put("status" , "completed");
        map2.put("order_type_name" , "IMPORT");
        map2.put("user_name" , "admin");
        map2.put("manufacturer_name" , "Công Ty cổ phần tập đoàn giống cây trồng Việt Nam – chi nhánh Hà Nam VINASEED");
        map2.put("is_return" , null);
        map2.put("description" , null);
        Timestamp createdDate = Timestamp.valueOf("2022-11-06 18:34:07.000000");
        map2.put("update_date" , null);
        map2.put("confirm_date" , null);
        map2.put("created_date" , createdDate);
        map2.put("confirm_by_name" , "admin");
        map2.put("order_code" , "1");
        BigInteger id2 = BigInteger.valueOf(1);
        map2.put("id" , id2);
        orderList1.add(map2);
        Integer status = null;

        LocalDateTime dateFrom = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-11-06 00:00:00", formatter);
        dateFrom = dateTime;
        LocalDateTime dateTo = null;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-16 00:00:00", formatter1);
        dateTo = dateTime1;

        Long userID = null;
        String orderCode = "";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithStatusDateFromAndDateTo() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Integer status = 1;

        LocalDateTime dateFrom = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-11-06 00:00:00", formatter);
        dateFrom = dateTime;
        LocalDateTime dateTo = null;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-16 00:00:00", formatter1);
        dateTo = dateTime1;

        Long userID = null;
        String orderCode = "";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }


    @Test
    void getListImportOrdersWithFullParameter() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Integer status = 1;

        LocalDateTime dateFrom = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-11-06 00:00:00", formatter);
        dateFrom = dateTime;
        LocalDateTime dateTo = null;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-16 00:00:00", formatter1);
        dateTo = dateTime1;
        Long userID = null;
        String orderCode = "2";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithStatusAndOrderCode() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        Map<String,Object> map1= new HashMap<>();
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);
        Integer status = 1;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        Long userID = null;
        String orderCode = "2";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithStatus() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        Map<String,Object> map1= new HashMap<>();
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);
        Integer status = 1;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        Long userID = null;
        String orderCode = "";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithStatusDateFromAndDateToAndOrderCode() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Integer status = 1;

        LocalDateTime dateFrom = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-11-06 00:00:00", formatter);
        dateFrom = dateTime;
        LocalDateTime dateTo = null;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-16 00:00:00", formatter1);
        dateTo = dateTime1;

        Long userID = null;
        String orderCode = "2";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }


    @Test
    void getListImportOrdersWithDateFromAndOrderCode() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Integer status = null;

        LocalDateTime dateFrom = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-11-06 00:00:00", formatter);
        dateFrom = dateTime;
        LocalDateTime dateTo = null;
//        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-01 09:46:04", formatter1);
//        dateTo = dateTime1;

        Long userID = null;
        String orderCode = "2";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithDateToAndOrderCode() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);

        Integer status = null;
        LocalDateTime dateFrom = null;
        LocalDateTime dateTo = null;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-16 00:00:00", formatter1);
        dateTo = dateTime1;

        Long userID = null;
        String orderCode = "2";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }

    @Test
    void getListImportOrdersWithDateFromAndDateToAndOrderCode() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by("id").descending());
        List<Map<String, Object>> orderList1 = new ArrayList<>();
        Map<String,Object> map5= new HashMap<>();
        double totalPrice5 = 4.05E7;
        map5.put("total_price" , totalPrice5);
        map5.put("status" , "pending");
        map5.put("order_type_name" , "IMPORT");
        map5.put("user_name" , "admin");
        map5.put("manufacturer_name" , "Cty cổ phần bảo vệ thực vật 1 trung ương ");
        map5.put("is_return" , null);
        map5.put("description" , null);
        Timestamp createdDate5 = Timestamp.valueOf("2022-12-16 01:32:38.967000");
        map5.put("update_date" ,null);
        map5.put("confirm_date" , null);
        map5.put("created_date" , createdDate5);
        map5.put("confirm_by_name" , "admin");
        map5.put("order_code" , "2");
        BigInteger id5 = BigInteger.valueOf(47);
        map5.put("id" , id5);
        orderList1.add(map5);
        Integer status = null;

        LocalDateTime dateFrom = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse("2022-11-06 00:00:00", formatter);
        dateFrom = dateTime;
        LocalDateTime dateTo = null;
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime1 = LocalDateTime.parse("2022-12-16 00:00:00", formatter1);
        dateTo = dateTime1;

        Long userID = null;
        String orderCode = "2";
        List<Map<String, Object>> orderList = importOrderRepository.
                getListImportOrders(status, dateFrom, dateTo, userID, orderCode, pageable);
        assertEquals(orderList1.size(), orderList.size());
        for(int i = 0 ; i < orderList1.size() ; i++){
            System.out.println(orderList1.get(i).get("id") + " ss " + orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("id"), orderList.get(i).get("id"));
            assertEquals(orderList1.get(i).get("total_price"), orderList.get(i).get("total_price"));
            assertEquals(orderList1.get(i).get("status"), orderList.get(i).get("status"));
            assertEquals(orderList1.get(i).get("order_type_name"), orderList.get(i).get("order_type_name"));
            assertEquals(orderList1.get(i).get("update_date"), orderList.get(i).get("update_date"));
            assertEquals(orderList1.get(i).get("user_name"), orderList.get(i).get("user_name"));
            assertEquals(orderList1.get(i).get("manufacturer_name"), orderList.get(i).get("manufacturer_name"));
            assertEquals(orderList1.get(i).get("is_return"), orderList.get(i).get("is_return"));
            assertEquals(orderList1.get(i).get("description"), orderList.get(i).get("description"));
            assertEquals(orderList1.get(i).get("confirm_date"), orderList.get(i).get("confirm_date"));
            assertEquals(orderList1.get(i).get("created_date"), orderList.get(i).get("created_date"));
            assertEquals(orderList1.get(i).get("confirm_by_name"), orderList.get(i).get("confirm_by_name"));
            assertEquals(orderList1.get(i).get("order_code"), orderList.get(i).get("order_code"));
        }
    }


    @Test
    void confirmOrderWithHaveNoOrderId(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Đơn hàng không tồn tại !");
        messageResponse.setStatus(Constant.FAIL);
        Long orderId = null;
        Long confirmBy = 1L;
        ResponseEntity response = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        ResponseEntity confirmOrder = importOrderService.confirmOrder(orderId, confirmBy);
        MessageResponse messageResponse1 = (MessageResponse) confirmOrder.getBody();
        assertEquals(messageResponse.getMessage(), messageResponse1.getMessage());
        assertEquals(messageResponse.getStatus(), messageResponse1.getStatus());
        assertEquals(confirmOrder.getStatusCodeValue(), response.getStatusCodeValue());
        System.out.println(confirmOrder.getStatusCodeValue() + "jaha");
    }


    @Test
    void confirmOrderWithHaveNoConfirmBy(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Người xác nhận không thể bỏ trống !");
        messageResponse.setStatus(Constant.FAIL);
        Long orderId = 1L;
        Long confirmBy = null;
        ResponseEntity response = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        ResponseEntity confirmOrder = importOrderService.confirmOrder(orderId, confirmBy);
        MessageResponse messageResponse1 = (MessageResponse) confirmOrder.getBody();
        assertEquals(messageResponse.getMessage(), messageResponse1.getMessage());
        assertEquals(messageResponse.getStatus(), messageResponse1.getStatus());
        assertEquals(confirmOrder.getStatusCodeValue(), response.getStatusCodeValue());
        System.out.println(confirmOrder.getStatusCodeValue() + "jaha");
    }

    @Test
    void confirmOrderWithOrderIdNotExist(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Xác nhận đơn hàng thất bại !");
        messageResponse.setStatus(Constant.FAIL);
        Long orderId = 0L;
        Long confirmBy = 1L;
        ResponseEntity response = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        ResponseEntity confirmOrder = importOrderService.confirmOrder(orderId, confirmBy);
        MessageResponse messageResponse1 = (MessageResponse) confirmOrder.getBody();
        assertEquals(messageResponse.getMessage(), messageResponse1.getMessage());
        assertEquals(messageResponse.getStatus(), messageResponse1.getStatus());
        assertEquals(response.getStatusCodeValue(), confirmOrder.getStatusCodeValue());
        System.out.println(confirmOrder.getStatusCodeValue() + "jaha");
    }

    @Test
    void confirmOrderWithOrderIdConfirmed(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Xác nhận đơn hàng thất bại !");
        messageResponse.setStatus(Constant.FAIL);
        Long orderId = 1L;
        Long confirmBy = 1L;
        ResponseEntity response = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        ResponseEntity confirmOrder = importOrderService.confirmOrder(orderId, confirmBy);
        MessageResponse messageResponse1 = (MessageResponse) confirmOrder.getBody();
        assertEquals(messageResponse.getMessage(), messageResponse1.getMessage());
        assertEquals(messageResponse.getStatus(), messageResponse1.getStatus());
        assertEquals(response.getStatusCodeValue(), confirmOrder.getStatusCodeValue());
        System.out.println(confirmOrder.getStatusCodeValue() + "jaha");
    }

    @Test
    void confirmOrderNormalCase(){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Xác nhận nhập hàng thành công !!");
        Long orderId = 47L;
        Long confirmBy = 1L;
        ResponseEntity response = new ResponseEntity(responseVo, HttpStatus.OK);
        ResponseEntity confirmOrder = importOrderService.confirmOrder(orderId, confirmBy);
        ResponseVo responseVo1 = (ResponseVo) confirmOrder.getBody();
        assertEquals(responseVo.getMessage(), responseVo1.getMessage());
        assertEquals(response.getStatusCodeValue(), confirmOrder.getStatusCodeValue());
        System.out.println(confirmOrder.getStatusCodeValue() + "jaha");
    }




    @Test
    void cancelOrderWithHaveNoOrderId(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Đơn hàng không tồn tại !");
        messageResponse.setStatus(Constant.FAIL);
        Long orderId = null;
        Long confirmBy = 1L;
        ResponseEntity response = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        ResponseEntity cancelOrder = importOrderService.cancelOrder(orderId, confirmBy);
        MessageResponse messageResponse1 = (MessageResponse) cancelOrder.getBody();
        assertEquals(messageResponse.getMessage(), messageResponse1.getMessage());
        assertEquals(messageResponse.getStatus(), messageResponse1.getStatus());
        assertEquals(cancelOrder.getStatusCodeValue(), response.getStatusCodeValue());
        System.out.println(cancelOrder.getStatusCodeValue() + "jaha");
    }

    @Test
    void cancelOrderWithHaveNoConfirmBy(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Người xác nhận không thể bỏ trống !");
        messageResponse.setStatus(Constant.FAIL);
        Long orderId = 1L;
        Long confirmBy = null;
        ResponseEntity response = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        ResponseEntity cancelOrder = importOrderService.cancelOrder(orderId, confirmBy);
        MessageResponse messageResponse1 = (MessageResponse) cancelOrder.getBody();
        assertEquals(messageResponse.getMessage(), messageResponse1.getMessage());
        assertEquals(messageResponse.getStatus(), messageResponse1.getStatus());
        assertEquals(cancelOrder.getStatusCodeValue(), response.getStatusCodeValue());
        System.out.println(cancelOrder.getStatusCodeValue() + "jaha");
    }

    @Test
    void cancelOrderWithOrderIdNotExist(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Xác nhận đơn hàng thất bại !");
        messageResponse.setStatus(Constant.FAIL);
        Long orderId = 0L;
        Long confirmBy = 1L;
        ResponseEntity response = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        ResponseEntity cancelOrder = importOrderService.cancelOrder(orderId, confirmBy);
        MessageResponse messageResponse1 = (MessageResponse) cancelOrder.getBody();
        assertEquals(messageResponse.getMessage(), messageResponse1.getMessage());
        assertEquals(messageResponse.getStatus(), messageResponse1.getStatus());
        assertEquals(response.getStatusCodeValue(), cancelOrder.getStatusCodeValue());
        System.out.println(cancelOrder.getStatusCodeValue() + "jaha");
    }

    @Test
    void cancelOrderWithOrderIdConfirmed(){
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Xác nhận đơn hàng thất bại !");
        messageResponse.setStatus(Constant.FAIL);
        Long orderId = 1L;
        Long confirmBy = 1L;
        ResponseEntity response = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        ResponseEntity cancelOrder = importOrderService.cancelOrder(orderId, confirmBy);
        MessageResponse messageResponse1 = (MessageResponse) cancelOrder.getBody();
        assertEquals(messageResponse.getMessage(), messageResponse1.getMessage());
        assertEquals(messageResponse.getStatus(), messageResponse1.getStatus());
        assertEquals(response.getStatusCodeValue(), cancelOrder.getStatusCodeValue());
        System.out.println(cancelOrder.getStatusCodeValue() + "jaha");
    }

    @Test
    void cancelOrderNormalCase(){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Hủy xác nhận nhập hàng thành công !!");
        Long orderId = 47L;
        Long confirmBy = 1L;
        ResponseEntity response = new ResponseEntity(responseVo, HttpStatus.OK);
        ResponseEntity cancelOrder = importOrderService.cancelOrder(orderId, confirmBy);
        ResponseVo responseVo1 = (ResponseVo) cancelOrder.getBody();
        assertEquals(responseVo.getMessage(), responseVo1.getMessage());
        assertEquals(response.getStatusCodeValue(), cancelOrder.getStatusCodeValue());
        System.out.println(cancelOrder.getStatusCodeValue() + "jaha");
    }


    @Test
    void getOrderDetailWithNormalCase() {
        Long orderId = 1L;
        ServiceResult serviceResult = importOrderService.getImportOderDetail(orderId);
        ServiceResult serviceResult1 = new ServiceResult<>();

        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map2 = new HashMap<>();
        BigInteger id2 = BigInteger.valueOf(1);
        map2.put("id", id2);
        map2.put("creator", "admin");
        map2.put("product_name", "Giống lúa Khang dân đột biến");
        BigInteger warehouseId2 = BigInteger.valueOf(1);
        map2.put("warehouse_id", warehouseId2);
        map2.put("product_code", "KDB");
        BigInteger orderId2 = BigInteger.valueOf(1);
        map2.put("order_id", orderId2);
        BigInteger creatorId2 = BigInteger.valueOf(1);
        map2.put("creator_id", creatorId2);
        BigInteger confirmById2 = BigInteger.valueOf(1);
        map2.put("confirm_by_id", confirmById2);
        map2.put("warehouse_name", "Kho A3");
        BigInteger productId2 = BigInteger.valueOf(1);
        map2.put("product_id", productId2);
        map2.put("unit_price", 21000.0);
        map2.put("quantity", 200);
        map2.put("unit_measure", "kg");
        map2.put("confirm_by", "admin");
        BigInteger consignmentId2 = BigInteger.valueOf(1);
        map2.put("consignment_id", consignmentId2);
        Timestamp expirationDate2 = Timestamp.valueOf("2024-11-06 18:34:07.000000");
        map2.put("expiration_date", expirationDate2);
        map2.put("name", "Công Ty cổ phần tập đoàn giống cây trồng Việt Nam – chi nhánh Hà Nam VINASEED");
        list.add(map2);

        Map<String, Object> map1 = new HashMap<>();
        BigInteger id = BigInteger.valueOf(1);
        map1.put("id", id);
        map1.put("creator", "admin");
        map1.put("product_name", "Giống lúa thuần năng suất chất lượng VNR20");
        BigInteger warehouseId = BigInteger.valueOf(1);
        map1.put("warehouse_id", warehouseId);
        map1.put("product_code", "VNR20");
        BigInteger orderId1 = BigInteger.valueOf(1);
        map1.put("order_id", orderId1);
        BigInteger creatorId = BigInteger.valueOf(1);
        map1.put("creator_id", creatorId);
        BigInteger confirmById = BigInteger.valueOf(1);
        map1.put("confirm_by_id", confirmById);
        map1.put("warehouse_name", "Kho A3");
        BigInteger productId = BigInteger.valueOf(3);
        map1.put("product_id", productId);
        map1.put("unit_price", 20000.0);
        map1.put("quantity", 300);
        map1.put("unit_measure", "kg");
        map1.put("confirm_by", "admin");
        BigInteger consignmentId = BigInteger.valueOf(1);
        map1.put("consignment_id", consignmentId);
        Timestamp expirationDate = Timestamp.valueOf("2024-11-06 18:34:07.000000");
        map1.put("expiration_date", expirationDate);
        map1.put("name", "Công Ty cổ phần tập đoàn giống cây trồng Việt Nam – chi nhánh Hà Nam VINASEED");
        list.add(map1);
        Map<String, Object> output = new HashMap<>();
        output.put("listImportProduct", list);
        serviceResult1.setData(output);
        serviceResult1.setMessage("success");
        serviceResult1.setStatus(HttpStatus.OK);

        //get data expected
        Map<String, Object> outputExpected = new HashMap<>();
        outputExpected = (Map<String, Object>) serviceResult1.getData();
        List<Map<String, Object>> expected = new ArrayList<>();
        expected = (List<Map<String, Object>>) outputExpected.get("listImportProduct");

        //get data actual
        Map<String, Object> outputActual = new HashMap<>();
        outputActual = (Map<String, Object>) serviceResult.getData();
        List<Map<String, Object>> actual = new ArrayList<>();
        actual = (List<Map<String, Object>>) outputActual.get("listImportProduct");

        assertEquals(expected.size(), actual.size());
        for(int i =0; i< expected.size(); i++){
            assertEquals(expected.get(i).get("id"), actual.get(i).get("id"));
            assertEquals(expected.get(i).get("name"), actual.get(i).get("name"));
            assertEquals(expected.get(i).get("expiration_date"), actual.get(i).get("expiration_date"));
            assertEquals(expected.get(i).get("consignment_id"), actual.get(i).get("consignment_id"));
            assertEquals(expected.get(i).get("confirm_by"), actual.get(i).get("confirm_by"));
            assertEquals(expected.get(i).get("unit_measure"), actual.get(i).get("unit_measure"));
            assertEquals(expected.get(i).get("quantity"), actual.get(i).get("quantity"));
            assertEquals(expected.get(i).get("unit_price"), actual.get(i).get("unit_price"));
            assertEquals(expected.get(i).get("product_id"), actual.get(i).get("product_id"));
            assertEquals(expected.get(i).get("warehouse_name"), actual.get(i).get("warehouse_name"));
            assertEquals(expected.get(i).get("confirm_by_id"), actual.get(i).get("confirm_by_id"));
            assertEquals(expected.get(i).get("creator_id"), actual.get(i).get("creator_id"));
            assertEquals(expected.get(i).get("order_id"), actual.get(i).get("order_id"));
            assertEquals(expected.get(i).get("product_code"), actual.get(i).get("product_code"));
            assertEquals(expected.get(i).get("warehouse_id"), actual.get(i).get("warehouse_id"));
            assertEquals(expected.get(i).get("product_name"), actual.get(i).get("product_name"));
            assertEquals(expected.get(i).get("creator"), actual.get(i).get("creator"));
        }
    }

    @Test
    void getOrderDetailWithOrderIdNotExist() {
        Long orderId = 0L;
        ServiceResult serviceResult = importOrderService.getImportOderDetail(orderId);
        ServiceResult serviceResult1 = new ServiceResult<>();
        serviceResult1.setMessage("success");
        serviceResult1.setStatus(HttpStatus.OK);
        assertEquals(serviceResult1.getMessage(), serviceResult.getMessage());
        assertEquals(serviceResult1.getStatus(), serviceResult.getStatus());
        System.out.println(serviceResult1.getMessage() +" " + serviceResult.getStatus());
    }

    @Test
    void editOrderWithOrderWithNormalCase(){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setMessage("Sửa thông tin nhập hàng thành công !!");
        Long orderId = 47L;
        List<ConsignmentProductDTO> consignmentProductDTOList = new ArrayList<>();
        ConsignmentProductDTO consignmentProductDTO = new ConsignmentProductDTO();
        consignmentProductDTO.setProductId(1L);
        consignmentProductDTO.setConsignmentId(1L);
        consignmentProductDTO.setQuantity(10);
        consignmentProductDTO.setUnitPrice(1000.0);
        consignmentProductDTO.setExpirationDate("2022-11-06 00:00:00");
        consignmentProductDTO.setWarehouseId(1L);
        consignmentProductDTOList.add(consignmentProductDTO);
        ResponseEntity response = importOrderService.editOrder(orderId,consignmentProductDTOList);
        ResponseVo responseVo1 = (ResponseVo) response.getBody();
        assertEquals(responseVo.getMessage(),responseVo1.getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
