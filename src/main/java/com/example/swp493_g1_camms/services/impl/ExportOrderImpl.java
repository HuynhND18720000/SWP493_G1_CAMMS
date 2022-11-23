package com.example.swp493_g1_camms.services.impl;

import com.example.swp493_g1_camms.entities.*;
import com.example.swp493_g1_camms.payload.request.*;
import com.example.swp493_g1_camms.payload.response.*;
import com.example.swp493_g1_camms.repository.*;
import com.example.swp493_g1_camms.services.interfaceService.IExportOrderService;
import com.example.swp493_g1_camms.utils.ConvertDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExportOrderImpl implements IExportOrderService {
    @Autowired
    IExportOrderRepository exportOrderRepository;
    @Autowired
    IWarehouseRepository warehouseRepository;
    @Autowired
    IOrderRepository orderRepository;
    @Autowired
    ConvertDateUtils convertDateUtils;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IManufacturerRepository manufacturerRepository;
    @Autowired
    IOrderTypeRepository orderTypeRepository;
    @Autowired
    IStatusRepository statusRepository;
    @Autowired
    IConsignmentRepository consignmentRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    IOrderDetailRepository orderDetailRepository;
    @Autowired
    IConsignmentProductRepository consignmentProductRepository;
    @Override
    public ResponseEntity<?> createExportOrder(ExportOrderRequest exportOrderRequest) {
        Order order = new Order();
        Status status = new Status();
        ResponseVo responseVo = new ResponseVo();
        MessageResponse messageResponse = new MessageResponse();
        try{
            //add vao bảng order
            List<Order> lo = orderRepository.getLastOrderCode();
            Order o = lo.get(0);
            int new_orderCode = Integer.parseInt((o.getId()+"").trim()) + 1;
            order.setOrderCode(String.valueOf(new_orderCode));
            order.setCreatedDate(convertDateUtils.convertDateFormat());
            order.setIsReturn(false);
            order.setDeletedAt(false);
            Optional<User> user = userRepository.getUserById(exportOrderRequest.getUser_Id());
            User u = user.get();
            if (u==null){
                responseVo.setMessage("User khong ton tai");
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
            order.setUser(u);
            order.setOrderType(orderTypeRepository.getOrderTypeById(Long.valueOf(2)));
            order.setStatus(statusRepository.findStatusById(Long.valueOf(1)));
            orderRepository.save(order);

            //lay ra list product muon suat
            List<ProductForExport> productForExportList = exportOrderRequest.getProductForExport();
            //list consignment code
            List<Long> listConsignmentCode = exportOrderRepository.getConsignmentCode();
            System.out.println("last consignmentCode: "+ listConsignmentCode.size());
            int last_consignment = listConsignmentCode.size();
            //set consignment code for list product
            Long consignmentCode = Long.valueOf(last_consignment+1);
            System.out.println("consignmentCode: "+ consignmentCode);
            //set date create export order
            Date in = new Date();
            LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
            //list info cosignment cua 1 product
            for (ProductForExport p: productForExportList
                 ) {
                //lay ra id cua product
                Long product_id = p.getProduct_id();
                //lay ra warehouse tu list consignment khi get ve
                List<ListConsignmentProductExport> consignmentProductExportList =
                        p.getConsignmentProductExportList();
                //thong tin consignment nhap san pham
                for (ListConsignmentProductExport lcpx:consignmentProductExportList
                     ) {
                    //tao consignment
                    Consignment consignmentForExport = new Consignment();
                    consignmentForExport.setImportDate(ldt);
                    consignmentForExport.setConsignment_code(consignmentCode);
                    consignmentForExport.setDeletedAt(false);
                    //lay ra warehouse cua san pham muon xuat
                    Long warehouse_id = lcpx.getWareHouseId();
                    Warehouse warehouse_for_consignment = new Warehouse();
                    warehouse_for_consignment.setId(warehouse_id);
                    consignmentForExport.setWarehouse(warehouse_for_consignment);

                    consignmentRepository.save(consignmentForExport);
                    System.out.println("tao thanh cong consignment cho product");
                }

            }
            //lay ra list consignment sau khi tao thanh cong de them vao bang order detail
            List<Consignment> consignmentList_after_create_export =
                    consignmentRepository.getListConsignmentExportByConsignmentCode(consignmentCode);
            System.out.println("so luong consignment trong list"+ consignmentList_after_create_export.size());

            Order order_after_create = orderRepository.getOrderByOrderCode(order.getOrderCode());

            for (Consignment c:consignmentList_after_create_export
                 ) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order_after_create);
                orderDetail.setConsignment(c);
                orderDetailRepository.save(orderDetail);
                System.out.println("tao thanh cong order detail");
            }
            //thao tac voi bang consignment_product
            int index_of_consignment = 0;
            for (ProductForExport p: productForExportList
            ) {
                //lay ra id cua product
                Long product_id = p.getProduct_id();
                Product product = new Product();
                product.setId(product_id);

                List<ListConsignmentProductExport> listConsignmentProductExports
                        = p.getConsignmentProductExportList();
                //list consignment nhap cua 1 product
                for (ListConsignmentProductExport lcpe: listConsignmentProductExports
                     ) {
                    ConsignmentProduct consignmentProduct = new ConsignmentProduct();
                    consignmentProduct.setProduct(product);
                    consignmentProduct.setQuantity(lcpe.getQuantity());
                    double unitprice_sale = productRepository.totalPrice(product_id);
                    consignmentProduct.setUnitPrice(unitprice_sale);
                    consignmentProduct.setDeletedAt(false);
                    String expDate = lcpe.getExpirationDate();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime dateTime = LocalDateTime.parse(expDate, formatter);
                    consignmentProduct.setExpirationDate(dateTime);
                    consignmentProduct.setMark_get_product_from_consignment(lcpe.getConsignment_id());
                    System.out.println("mark_con la:"+consignmentProduct.getMark_get_product_from_consignment());
                    Consignment consignment_for_exportOrder =
                            consignmentList_after_create_export.get(index_of_consignment);
                    consignmentProduct.setConsignment(consignment_for_exportOrder);
                    consignmentProductRepository.save(consignmentProduct);

                    index_of_consignment++;
                }
                System.out.println("them vao bang consignment_product thanh cong");

            }
            messageResponse.setMessage("Tao phieu xuat hang thanh cong");
            return new ResponseEntity<>(messageResponse,HttpStatus.OK);

        }catch(Exception e){
            System.out.println("loi khong tao dc");
            messageResponse.setMessage(e+"");
            return ResponseEntity
                    .badRequest()
                    .body(messageResponse);
        }
    }

    @Override
    public ResponseEntity<?> getInfoProductInWareHouse(Long product_id,Integer pageIndex, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        List<Map<String, Object>> listProduct = exportOrderRepository.getProductInWareHouse(product_id);
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
            exportOrderResponse.setQuantity(0);
            exportOrderResponse.setUnitPrice((Double) lp.get("unitPrice"));
            exportOrderResponse.setUnitMeasure(lp.get("unitMeasure").toString());
            exportOrderResponseList.add(exportOrderResponse);
            //can nhac co de them cot thanh tien hay ko
            //so luong record product
            total_Product_In_Consignment += 1;

            //hien thi list lo hang nhap san pham cos id la productId
            ListConsignmentProductResponse consignmentProductResponse = new ListConsignmentProductResponse();
            consignmentProductResponse.setId(Long.parseLong(lp.get("consignment_id").toString()));
            consignmentProductResponse.setWarehouseId(Long.parseLong(lp.get("wareHouseId").toString()));
            consignmentProductResponse.setWarehouseName(lp.get("warehouseName").toString());
            consignmentProductResponse.setImportDate(lp.get("importDate").toString());
            consignmentProductResponse.setExpirationDate(lp.get("expirationDate").toString());
            consignmentProductResponse.setQuantityInstock((Integer) lp.get("quantityInstock"));
            consignmentProductResponse.setQuantity(0);
            consignmentProductResponseList.add(consignmentProductResponse);
            exportOrderResponse.setConsignmentList(consignmentProductResponseList);
        }
        map.put("listConsignmentProduct",exportOrderResponse.getConsignmentList());
        map.put("listProduct",exportOrderResponse);
        map.put("pageIndex",pageIndex);
        map.put("pageSize",pageSize);
        map.put("totalRecord",total_Product_In_Consignment);
        map.put("status",200);
        responseVo.setData(map);
        responseVo.setMessage("lay list product va consignment cuar product ve thanh cong");
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListExportOrder(Integer pageIndex, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex-1, pageSize);
        ResponseVo responseVo = new ResponseVo();
        try {
            Page<Order> orderPage = exportOrderRepository.getListExportOrder(pageable);
            System.out.println("list danh sach order export la"+ orderPage.getContent());
            Map<String, Object> map = new HashMap<>();
            if(orderPage.isEmpty()){
                map.put("listExportOrder",orderPage.getContent());
                map.put("totalRecord", 0);
                responseVo.setMessage("List danh sach phieu xuat kho trong");
                responseVo.setData(map);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            map.put("listExportOrder", ListExportOrderResponse.createSuccessData(orderPage.getContent()));

            map.put("currentPage", pageIndex);
            map.put("pageSize", pageSize);
            map.put("totalPage", orderPage.getTotalPages());

            responseVo.setData(map);
            responseVo.setMessage("Lay du lieu thanh cong");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
