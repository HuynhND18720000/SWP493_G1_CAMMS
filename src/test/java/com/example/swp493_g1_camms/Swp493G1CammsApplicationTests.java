package com.example.swp493_g1_camms;

import com.example.swp493_g1_camms.payload.response.ListProductResponse;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.utils.ConvertDateUtils;
import com.example.swp493_g1_camms.utils.ConvertToEntities;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Swp493G1CammsApplicationTests {
    @Mock
    IProductRepository productRepository;
    @Mock
    ConvertToEntities convertToEntities;
    @Mock
    IOrderRepository orderRepository;
    @Mock
    ConvertDateUtils convertDateUtils;
    @Mock
    IUserRepository userRepository;
    @Mock
    IManufacturerRepository manufacturerRepository;
    @Mock
    IOrderTypeRepository orderTypeRepository;
    @Mock
    IStatusRepository statusRepository;
    @Mock
    IWarehouseRepository warehouseRepository;
    @Mock
    IConsignmentRepository consignmentRepository;
    @Mock
    IRelationConsignmentProductRepository relationConsignmentProductRepository;
    @Mock
    IOrderDetailRepository orderDetailRepository;
    @Mock
    IImportOrderRepository importOrderRepository;
    ListProductResponse listProductResponse;
    @Mock
    IConsignmentProductRepository consignmentProductRepository;

    @Test
    public void testCreateOrder() throws Exception{

    }
}
