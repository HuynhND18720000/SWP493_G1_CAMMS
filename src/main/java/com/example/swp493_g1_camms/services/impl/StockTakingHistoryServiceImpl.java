package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.*;
import com.example.swp493_g1_camms.payload.response.*;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.services.interfaceService.IStockTakingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class StockTakingHistoryServiceImpl implements IStockTakingHistoryService {

    @Autowired
    IStockTakingHistoryRepository stockTakingHistoryRepository;
    @Autowired
    IStockTakingHistoryDetailRepository stockTakingHistoryDetailRepository;

    @Autowired
    IConsignmentRepository consignmentRepository;

    @Autowired
    IConsignmentProductRepository consignmentProductRepository;
    @Autowired
    ProductRepository productRepository;



    @Override
    public ResponseEntity<?> findAllStockTakingHistory(Integer pageIndex, Integer pageSize, Long wareHouseId, Long userId, String startDate, String endDate, String orderBy) {
        String orderData = "createDate";
        if (orderBy != null) {
            orderData = orderBy;
        }
        Sort sort = Sort.by(orderData).descending();
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize, sort);
        if (endDate != null) {
            LocalDate endDateSearch = LocalDate.parse(endDate);
            endDate = endDateSearch.plusDays(1).toString();
        }
        Page<StockTakingHistory> pageStockTakingHistory = stockTakingHistoryRepository
                .findAllStockTakingHistory(wareHouseId, userId, startDate, endDate, pageable);

        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        if (pageStockTakingHistory.isEmpty()) {
            map.put("listStockTakingHistory", pageStockTakingHistory.getContent());
            map.put("totalRecord", 0);
            responseVo.setMessage("Không tìm thấy phiếu kiểm kho");
            responseVo.setData(map);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        map.put("listStockTakingHistory",
                ListStockTakingHistoryResponse.createListStockTakingHistory(pageStockTakingHistory.getContent()));
        map.put("currentPage", pageIndex);
        map.put("pageSize", pageStockTakingHistory.getSize());
        map.put("totalRecord", pageStockTakingHistory.getTotalElements());
        map.put("totalPage", pageStockTakingHistory.getTotalPages());
        responseVo.setData(map);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createStockTakingHistory() {
        return null;
    }

    @Override
    public ResponseEntity<?> getProducFromConsignmentInWarehouse() {
//        MessageResponse messageResponse = new MessageResponse();
//        try{
//            listProductResponse = new ListProductResponse();
//            ResponseVo responseVo = new ResponseVo();
//            List<Product> listProduct = productRepository.getAllProductByManufacturerId(id);
//
//            Map<String, Object> map = new HashMap<>();
//            if (listProduct.size() == 0) {
//                map.put("product", null);
//                map.put("totalRecord", 0);
//                responseVo.setMessage("Không tìm thấy List Manufacturer");
//                responseVo.setData(map);
//                return new ResponseEntity<>(responseVo, HttpStatus.OK);
//            }
//            map.put("product", listProductResponse.createSuccessData(listProduct));
//            responseVo.setData(map);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }catch(Exception e){
//            messageResponse.setMessage(e+"");
//            return ResponseEntity
//                    .badRequest()
//                    .body(messageResponse);
//        }
        return null;
    }


    @Override
    public ResponseEntity<?> findListDetailById(Long stockTakingHistoryId) {
        ResponseVo responseVo = new ResponseVo();
        if (!ObjectUtils.isEmpty(stockTakingHistoryId)) {

            StockTakingHistory stockTakingHistory = stockTakingHistoryRepository.findStockTakingHistoryById(stockTakingHistoryId);
            Map<String, Object> map = new HashMap<>();

            if (!ObjectUtils.isEmpty(stockTakingHistory)) {
                List<StockTakingHistoryDetail> listDetail = stockTakingHistoryDetailRepository.findAllByStockTakingHistoryId(stockTakingHistoryId);

                List<Long> listConsignmentId = new ArrayList<>();
                for (StockTakingHistoryDetail stockTakingHistoryDetail : listDetail) {
                    listConsignmentId.add(stockTakingHistoryDetail.getConsignment().getId());
                }

                //List<Consignment> ls = consignmentRepository.findAllConsignmentByListId(listConsignmentId);
                List<ConsignmentProduct> listConsignment = consignmentProductRepository.findAllConsignmentByListId(listConsignmentId);
                Set<Long> setProductId = new HashSet<>();
                for (ConsignmentProduct cp : listConsignment ) {
                    setProductId.add(cp.getProduct().getId());
                }
                List<Product> listProduct = productRepository.findListAllByConsignmentList(setProductId);

                if (listDetail.isEmpty()) {
                    responseVo.setMessage("Không tìm thấy chi tiết đơn kiểm kho");
                    responseVo.setData(map);
                }
                map.put("stockTakingHistoryDetail", StockTakingHistoryDetailResponse.createSuccessData(stockTakingHistory, listDetail, listProduct, listConsignment));
                responseVo.setData(map);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            responseVo.setMessage("Không tìm thấy đơn kiểm kho");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);

    }


}
