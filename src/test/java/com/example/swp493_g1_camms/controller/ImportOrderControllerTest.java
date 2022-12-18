package com.example.swp493_g1_camms.controller;

import com.example.swp493_g1_camms.payload.request.ConsignmentProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class ImportOrderControllerTest {
    @Autowired
    ImportOrderController importOrderController;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void confirmOrderWithOrderIdIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/confirm?orderId=Minh&confirmBy=1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void confirmOrderWithNoOrderId() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/confirm?orderId=&confirmBy=1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void confirmOrderWithConfirmByIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/confirm?orderId=1&confirmBy=Minh";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void confirmOrderWithNoConfirmBy() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/confirm?orderId=1&confirmBy=";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void cancelOrderWithOrderIdIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/cancel?orderId=Minh&confirmBy=1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void cancelOrderWithNoOrderId() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/cancel?orderId=&confirmBy=1";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void cancelOrderWithConfirmByIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/cancel?orderId=1&confirmBy=Minh";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void cancelOrderWithNoConfirmBy() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/cancel?orderId=1&confirmBy=Minh";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void editOrderWithOrderIdIsString() throws Exception {
        List<ConsignmentProductDTO> consignmentProductDTOList = new ArrayList<>();
        ConsignmentProductDTO consignmentProductDTO = new ConsignmentProductDTO();
        consignmentProductDTO.setProductId(3L);
        consignmentProductDTO.setExpirationDate("2025-05-09 12:32:32");
        consignmentProductDTO.setConsignmentId(1L);
        consignmentProductDTO.setQuantity(100);
        consignmentProductDTO.setUnitPrice(10000.0);
        consignmentProductDTOList.add(consignmentProductDTO);
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/editOrder?orderId=Minh";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consignmentProductDTOList)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void editOrderWithNoOrderId() throws Exception {
        List<ConsignmentProductDTO> consignmentProductDTOList = new ArrayList<>();
        ConsignmentProductDTO consignmentProductDTO = new ConsignmentProductDTO();
        consignmentProductDTO.setProductId(3L);
        consignmentProductDTO.setExpirationDate("2025-05-09 12:32:32");
        consignmentProductDTO.setConsignmentId(1L);
        consignmentProductDTO.setQuantity(100);
        consignmentProductDTO.setUnitPrice(10000.0);
        consignmentProductDTOList.add(consignmentProductDTO);
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/editOrder?orderId=";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consignmentProductDTOList)))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void getListImportOrderWithStatusIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/list?status=aaa";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void getListImportOrderWithDateFromIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/list?dateFrom=aaa";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void getListImportOrderWithDateToIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/list?dateTo=aaa";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void getListImportOrderWithPageIndexIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/list?pageIndex=aaa";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void getListImportOrderWithPageSizeIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/list?pageSize=aaa";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }

    @Test
    void getOrderDetailWithOrderIdIsString() throws Exception {
        ResponseEntity response = new ResponseEntity("", HttpStatus.BAD_REQUEST);

        String uri = "/api/import/getOrderDetail?orderId=aaa";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(response.getStatusCodeValue(),status);
        System.out.println(status + "jaha");
    }
}
