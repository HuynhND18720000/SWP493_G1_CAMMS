package com.example.swp493_g1_camms;

import com.example.swp493_g1_camms.controller.AuthController;
import com.example.swp493_g1_camms.controller.ImportOrderController;
import com.example.swp493_g1_camms.entities.User;
import com.example.swp493_g1_camms.payload.request.ConsignmentRequest;
import com.example.swp493_g1_camms.payload.request.ImportOrderRequest;
import com.example.swp493_g1_camms.payload.request.ProductRequest;
import com.example.swp493_g1_camms.payload.response.ListProductResponse;
import com.example.swp493_g1_camms.payload.response.MessageResponse;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.security.jwt.JwtUtils;
import com.example.swp493_g1_camms.security.services.UserDetailsImpl;
import com.example.swp493_g1_camms.services.impl.ImportOrderServiceImpl;
import com.example.swp493_g1_camms.services.interfaceService.IImportOrderService;
import com.example.swp493_g1_camms.utils.Constant;
import com.example.swp493_g1_camms.utils.ConvertDateUtils;
import com.example.swp493_g1_camms.utils.ConvertToEntities;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class Swp493G1CammsApplicationTests {

    @Mock
    IImportOrderService importOrderService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {

    }
    @Test
    void testImportOrderControllerSuccess() throws Exception {
        Long user_id = 1L;
        Long warehouse_id = 1L;
        Long manufacturer_id = 1L;
        Long product_id = 1L;

        ImportOrderRequest importOrderRequest = new ImportOrderRequest();
        importOrderRequest.setUser_Id(user_id);
        importOrderRequest.setWarehouseId(warehouse_id);
        importOrderRequest.setManufacturerId(manufacturer_id);

        ConsignmentRequest consignmentRequest = new ConsignmentRequest();
        consignmentRequest.setImportDate("2022-11-22 00:00:00");

        List<ProductRequest> productRequestList = new ArrayList<>();
        ProductRequest productRequest = new ProductRequest();
        productRequest.setImport_date("2022-11-22 00:00:00");
        productRequest.setId(product_id);
        productRequest.setQuantity(900);
        productRequest.setUnit_price(1000.0);
        productRequest.setExpiration_date("2022-12-22 00:00:00");
        productRequestList.add(productRequest);

        consignmentRequest.setProductRequestList(productRequestList);
        importOrderRequest.setConsignmentRequest(consignmentRequest);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Tạo phiếu nhập kho thành công !");
        messageResponse.setStatus(Constant.SUCCESS);

        String uri = "/api/import/createOrder";
        ResponseEntity ResponseEntity = new ResponseEntity(messageResponse, HttpStatus.OK);
        when(importOrderService.createOrder(importOrderRequest)).thenReturn(ResponseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(importOrderRequest)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
    //input is null
    @Test
    void testCreateImportOrderFail() throws Exception{
        Long user_id = null;
        Long warehouse_id = null;
        Long manufacturer_id = null;
        Long product_id = null;

        ImportOrderRequest importOrderRequest = new ImportOrderRequest();
        importOrderRequest.setUser_Id(user_id);
        importOrderRequest.setWarehouseId(warehouse_id);
        importOrderRequest.setManufacturerId(manufacturer_id);

        ConsignmentRequest consignmentRequest = new ConsignmentRequest();
        consignmentRequest.setImportDate(null);

        List<ProductRequest> productRequestList = null;

        consignmentRequest.setProductRequestList(productRequestList);
        importOrderRequest.setConsignmentRequest(consignmentRequest);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Tạo phiếu nhập kho thất bại !");
        messageResponse.setStatus(Constant.FAIL);

        String uri = "/api/import/createOrder";
        ResponseEntity ResponseEntity = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        when(importOrderService.createOrder(importOrderRequest)).thenReturn(ResponseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(importOrderRequest)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    //input quantity must be large than 0 and is number
    @Test
    void testCreateImportOrderFailWithQuantity() throws Exception{
        Long user_id = null;
        Long warehouse_id = null;
        Long manufacturer_id = null;
        Long product_id = null;

        ImportOrderRequest importOrderRequest = new ImportOrderRequest();
        importOrderRequest.setUser_Id(user_id);
        importOrderRequest.setWarehouseId(warehouse_id);
        importOrderRequest.setManufacturerId(manufacturer_id);

        ConsignmentRequest consignmentRequest = new ConsignmentRequest();
        consignmentRequest.setImportDate(null);

        List<ProductRequest> productRequestList = null;

        consignmentRequest.setProductRequestList(productRequestList);
        importOrderRequest.setConsignmentRequest(consignmentRequest);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Tạo phiếu nhập kho thất bại !");
        messageResponse.setStatus(Constant.FAIL);

        String uri = "/api/import/createOrder";
        ResponseEntity ResponseEntity = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        when(importOrderService.createOrder(importOrderRequest)).thenReturn(ResponseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(importOrderRequest)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }
    //input importPrice must be large than 0 and is number
    @Test
    void testCreateImportOrderFailWithImportPrice() throws Exception{
        Long user_id = null;
        Long warehouse_id = null;
        Long manufacturer_id = null;
        Long product_id = null;

        ImportOrderRequest importOrderRequest = new ImportOrderRequest();
        importOrderRequest.setUser_Id(user_id);
        importOrderRequest.setWarehouseId(warehouse_id);
        importOrderRequest.setManufacturerId(manufacturer_id);

        ConsignmentRequest consignmentRequest = new ConsignmentRequest();
        consignmentRequest.setImportDate(null);

        List<ProductRequest> productRequestList = null;

        consignmentRequest.setProductRequestList(productRequestList);
        importOrderRequest.setConsignmentRequest(consignmentRequest);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Tạo phiếu nhập kho thất bại !");
        messageResponse.setStatus(Constant.FAIL);

        String uri = "/api/import/createOrder";
        ResponseEntity ResponseEntity = new ResponseEntity(messageResponse, HttpStatus.BAD_REQUEST);
        when(importOrderService.createOrder(importOrderRequest)).thenReturn(ResponseEntity);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(importOrderRequest)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    //create stock taking

    
}
