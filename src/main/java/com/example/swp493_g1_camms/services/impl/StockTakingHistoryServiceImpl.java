package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.StockTakingHistory;
import com.example.swp493_g1_camms.payload.response.ListStockTakingHistoryResponse;
import com.example.swp493_g1_camms.payload.response.ResponseVo;
import com.example.swp493_g1_camms.repository.IStockTakingHistoryRepository;
import com.example.swp493_g1_camms.services.interfaceService.IStockTakingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class StockTakingHistoryServiceImpl implements IStockTakingHistoryService {

    @Autowired
    IStockTakingHistoryRepository stockTakingHistoryRepository;


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
        return null;
    }


    @Override
    public ResponseEntity<?> findListDetailById(Long stockTakingHistoryId) {
        return null;
    }


}
