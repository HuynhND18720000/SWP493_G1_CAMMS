package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.Order;
import com.example.swp493_g1_camms.entities.Product;
import com.example.swp493_g1_camms.payload.response.ListProductResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.IExportOrderRepository;
import com.example.swp493_g1_camms.repository.IImportOrderRepository;
import com.example.swp493_g1_camms.repository.IProductRepository;
import com.example.swp493_g1_camms.services.interfaceService.IDashboardService;
import com.example.swp493_g1_camms.services.interfaceService.IImportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements IDashboardService {
    @Autowired
    IProductRepository productRepository;
    @Autowired
    IImportOrderRepository importOrderRepository;
    @Autowired
    IExportOrderRepository exportOrderRepository;
    //lay ra so mat hang trong kho
    @Override
    public ResponseEntity<?> getTotalProductInWarehouse() {
        ResponseVo responseVo = new ResponseVo();
        try{
            List<Product> productList = productRepository.getAllProduct();
            int totalProduct = 0;
            for (Product p:productList
                 ) {
                totalProduct+=1;
            }
            responseVo.setMessage("lay danh sach sna pham trong kho thanh cong");
            responseVo.setData(totalProduct);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch(Exception e){
            responseVo.setMessage("khong the lay duoc danh sach sp "+e.getMessage());
            responseVo.setData(null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    //lay ra tong so don nhap kho
    @Override
    public ResponseEntity<?> getTotalImportOrder() {
        ResponseVo responseVo = new ResponseVo();
        try{
            List<Order> orderList = importOrderRepository.getTotalImportOrders();
            int totalProduct = 0;
            for (Order o:orderList
            ) {
                totalProduct+=1;
            }
            responseVo.setMessage("lay danh sach don nhap hang trong kho thanh cong");
            responseVo.setData(totalProduct);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch(Exception e){
            responseVo.setMessage("khong the lay duoc danh sach sp "+e.getMessage());
            responseVo.setData(null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> getTotalExportOrder() {
        ResponseVo responseVo = new ResponseVo();
        try{
            List<Order> orderList = exportOrderRepository.getTotalExportOrders();
            int totalProduct = 0;
            for (Order o:orderList
            ) {
                totalProduct+=1;
            }
            responseVo.setMessage("lay danh sach don xuat hang trong kho thanh cong");
            responseVo.setData(totalProduct);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch(Exception e){
            responseVo.setMessage("khong the lay duoc danh sach sp "+e.getMessage());
            responseVo.setData(null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }
}
