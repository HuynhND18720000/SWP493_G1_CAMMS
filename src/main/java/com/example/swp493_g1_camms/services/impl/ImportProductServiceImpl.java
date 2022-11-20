package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.ServiceResult;
import com.example.swp493_g1_camms.repository.IImportProductRepository;
import com.example.swp493_g1_camms.services.interfaceService.IImportProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ImportProductServiceImpl implements IImportProductService {

    @Autowired
    private IImportProductRepository iImportProductRepository;

    @Override
    public ServiceResult<Map<String, Object>> getListImportOrders(Integer pageIndex, Integer pageSize) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();
        Pageable pagable = PageRequest.of(pageIndex, pageSize,
                Sort.by("id").descending());

        try {
            List<Map<String, Object>> orderList = iImportProductRepository.getListImportGoodsBySearchData(pagable);
            BigInteger totalRecord = BigInteger.valueOf(0);
            if (!orderList.isEmpty()) {
                totalRecord = (BigInteger) orderList.get(0).get("totalRecord");
            }
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
    public ServiceResult<Map<String, Object>> getImportOderDetail(Integer orderId) {
        ServiceResult<Map<String, Object>> mapServiceResult = new ServiceResult<>();
        Map<String, Object> output = new HashMap<>();

        try {
            List<Map<String, Object>> orderDetail = iImportProductRepository.getImportOrderDetail(orderId);
            if (!orderDetail.isEmpty()) {

            }
            output.put("orderDetail", orderDetail);
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
}
