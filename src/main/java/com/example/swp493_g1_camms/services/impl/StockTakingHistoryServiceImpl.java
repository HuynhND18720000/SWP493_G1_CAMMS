package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.*;
import com.example.swp493_g1_camms.payload.request.StockTakingDetailsRequest;
import com.example.swp493_g1_camms.payload.request.StockTakingRequest;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    IProductRepository productRepository;

    @Autowired
    IExportOrderRepository exportOrderRepository;
    @Autowired
    IUserRepository userRepository;

    @Autowired
    IStockTakingHistoryDescriptionRepository stockTakingHistoryDescriptionRepository;

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
    public ResponseEntity<?> createStockTakingHistory(StockTakingRequest stockTakingRequest) {
        StockTakingHistory stockTakingHistory = new StockTakingHistory();
        ResponseVo responseVo = new ResponseVo();
        MessageResponse messageResponse = new MessageResponse();
        try{
            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
            //them vao bang stock taking history
            stockTakingHistory.setCreateDate(ldt);
            stockTakingHistory.setDeletedAt(false);
            Optional<User> user = userRepository.getUserById(stockTakingRequest.getUser_Id());
            User u = user.get();
            if (u==null){
                responseVo.setMessage("User khong ton tai");
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
            Warehouse warehouse = new Warehouse();
            warehouse.setId(stockTakingRequest.getWarehouse_Id());
            stockTakingHistory.setUser(u);
            stockTakingHistory.setWarehouse(warehouse);
            stockTakingHistory.setTotalDifferentAmount(stockTakingRequest.getTotal_Different_Amout());
            stockTakingHistoryRepository.save(stockTakingHistory);

            //get stockid
            Long stock_id = stockTakingHistoryRepository.getLastStockId();
            StockTakingHistory stockTakingHistory1 = new StockTakingHistory();
            stockTakingHistory1.setId(stock_id);

            //them vao bang stock taking history detail
            List<StockTakingDetailsRequest> stockTakingDetailsRequestList =
                    stockTakingRequest.getList_StockTakingDetails();
            for (StockTakingDetailsRequest stdr: stockTakingDetailsRequestList
                 ) {
                StockTakingHistoryDetail stockTakingHistoryDetail = new StockTakingHistoryDetail();

                stockTakingHistoryDetail.setInstockQuantity(stdr.getQuantityInstock());
                stockTakingHistoryDetail.setRealityQuantity(stdr.getQuantity());
                stockTakingHistoryDetail.setStockTakingHistory(stockTakingHistory);

                int real_quantity = stockTakingHistoryDetail.getRealityQuantity() -
                        stockTakingHistoryDetail.getInstockQuantity();
                double deviant_amount =
                        real_quantity
                                * stdr.getUnitPrice();
                if(real_quantity < 0){
                    deviant_amount *= -1;
                }

                stockTakingHistoryDetail.setDeviantAmount(deviant_amount);
                Consignment c = new Consignment();
                c.setId(stdr.getId());
                stockTakingHistoryDetail.setConsignment(c);
                System.out.println("consignment la:"+c.getId());

                Product p1 = new Product();
                p1.setId(stdr.getProductId());
                stockTakingHistoryDetail.setProduct(p1);
                System.out.println("product la:"+p1.getId());

                //update so luong product trong consignment_product
                ConsignmentProduct consignmentProduct = consignmentProductRepository.
                        getConsignmentProductById(c.getId(), p1.getId());
                System.out.println("consignment product la:"+consignmentProduct.getConsignment().getId());
                consignmentProduct.setQuantity_sale(stdr.getQuantity());
                consignmentProductRepository.save(consignmentProduct);

                //update so luong product tai bang product
                Product product = productRepository.findProductById(p1.getId());
                int quantity_of_product_before = product.getQuantity();
                if (real_quantity < 0){
                    quantity_of_product_before = product.getQuantity() - ((-1)*real_quantity);
                }else if(real_quantity > 0){
                    quantity_of_product_before = product.getQuantity() + real_quantity;
                }
                product.setQuantity(quantity_of_product_before);
                productRepository.save(product);

                StockTakingHistoryDescription stockTakingHistoryDescription = new StockTakingHistoryDescription();
                Product p2 = new Product();
                p2.setId(stdr.getProductId());
                stockTakingHistoryDescription.setProduct(p2);
                stockTakingHistoryDescription.setStockTakingHistory(stockTakingHistory1);
                stockTakingHistoryDescription.setConsignment(c);
                //check cho nay so luong chenh lech
                stockTakingHistoryDescription.setDifferentQuantity(stdr.getQuantityInstock() - stdr.getQuantity());
                stockTakingHistoryDescription.setDescription(stdr.getDescription());

                stockTakingHistoryDetailRepository.save(stockTakingHistoryDetail);
                stockTakingHistoryDescriptionRepository.save(stockTakingHistoryDescription);
            }
            messageResponse.setMessage("Tao phieu xuat hang thanh cong");
            return new ResponseEntity<>(messageResponse,HttpStatus.OK);
        }catch(Exception e){
            System.out.println("loi khong tao dc phieu xuat kho");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }

    }

    @Override
    public ResponseEntity<?> getProducFromConsignmentInWarehouse(Long warehouse_Id) {
        List<Product> listProductInWarehouse = null;
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> map = new HashMap<>();
        MessageResponse messageResponse = new MessageResponse();
        try{
            listProductInWarehouse = stockTakingHistoryRepository.getProductByWarehouse(warehouse_Id);
            map.put("listProductInWarehouse", ListProductResponse.createSuccessData(listProductInWarehouse));
            responseVo.setData(map);
            messageResponse.setMessage("lay thanh cong");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch(Exception e){
            System.out.println("loi khong lay dc");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }

    @Override
    public ResponseEntity<?> getInfoProductInWareHouse(Long product_id, Long warehouse_id) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> listProduct =
                exportOrderRepository.getProductInWareHouseForStockTaking(product_id, warehouse_id);
        List<ExportOrderResponse> exportOrderResponseList =  new ArrayList<>();
        List<ListConsignmentProductResponse> consignmentProductResponseList = new ArrayList<>();
        ExportOrderResponse exportOrderResponse = new ExportOrderResponse();
        ResponseVo responseVo = new ResponseVo();
        int total_Product_In_Consignment = 0;
        for (Map<String, Object> lp: listProduct
        ) {
            exportOrderResponse.setProductId(Long.parseLong(lp.get("productId").toString()));
            exportOrderResponse.setProductName(lp.get("productName").toString());
            exportOrderResponse.setProductCode(lp.get("productCode").toString());
            exportOrderResponse.setUnitPrice((Double) lp.get("unitPrice"));
            exportOrderResponse.setUnitMeasure(lp.get("unitMeasure").toString());
            exportOrderResponseList.add(exportOrderResponse);

            //so luong record product
            total_Product_In_Consignment += 1;

            //hien thi list lo hang nhap san pham cos id la productId
            ListConsignmentProductResponse consignmentProductResponse = new ListConsignmentProductResponse();
            consignmentProductResponse.setId(Long.parseLong(lp.get("consignment_id").toString()));
            consignmentProductResponse.setImportDate(lp.get("importDate").toString());
            consignmentProductResponse.setExpirationDate(lp.get("expirationDate").toString());
            consignmentProductResponse.setQuantityInstock(Integer.valueOf(lp.get("quantityInstock").toString()) );
            consignmentProductResponse.setQuantity(0);

            consignmentProductResponseList.add(consignmentProductResponse);
            exportOrderResponse.setConsignmentList(consignmentProductResponseList);
        }
        map.put("listConsignmentProduct",exportOrderResponse.getConsignmentList());
        map.put("listProduct",exportOrderResponse);
        map.put("totalRecord",total_Product_In_Consignment);
        map.put("status",200);
        responseVo.setData(map);
        responseVo.setMessage("lay list product va consignment cuar product ve thanh cong");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> findListDetailById(Long stockTakingHistoryId) {
        ResponseVo responseVo = new ResponseVo();
        if (!ObjectUtils.isEmpty(stockTakingHistoryId)) {

            StockTakingHistory stockTakingHistory = stockTakingHistoryRepository.findStockTakingHistoryById(stockTakingHistoryId);
            Map<String, Object> map = new HashMap<>();

            if (!ObjectUtils.isEmpty(stockTakingHistory)) {
                List<StockTakingHistoryDescription> listDescription = stockTakingHistoryDescriptionRepository.findAllDescriptionByStockTakingHistoryId(stockTakingHistoryId);
                List<StockTakingHistoryDetail> listDetail = stockTakingHistoryDetailRepository.findAllByStockTakingHistoryId(stockTakingHistoryId);

                List<Long> listConsignmentId = new ArrayList<>();
                for (StockTakingHistoryDetail stockTakingHistoryDetail : listDetail) {
                    listConsignmentId.add(stockTakingHistoryDetail.getConsignment().getId());
                }

                List<ConsignmentProduct> listConsignment = consignmentProductRepository.findAllConsignmentByListId(listConsignmentId,stockTakingHistoryId);
                Set<Long> setProductId = new HashSet<>();
                for (ConsignmentProduct cp : listConsignment ) {
                    setProductId.add(cp.getProduct().getId());
                }
                List<Product> listProduct = productRepository.findListAllByConsignmentList(setProductId);

                if (listDetail.isEmpty()) {
                    responseVo.setMessage("Không tìm thấy chi tiết đơn kiểm kho");
                    responseVo.setData(map);
                }

                map.put("stockTakingHistoryDetail", StockTakingHistoryDetailResponse.createSuccessData(stockTakingHistory, listDetail, listDescription, listProduct, listConsignment));
                responseVo.setData(map);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            responseVo.setMessage("Không tìm thấy đơn kiểm kho");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);

    }


}
