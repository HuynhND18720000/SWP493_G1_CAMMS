package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.ConsignmentProduct;
import com.example.swp493_g1_camms.entities.Order;
import com.example.swp493_g1_camms.entities.OrderDetail;
import com.example.swp493_g1_camms.repository.IConsignmentProductRepository;
import com.example.swp493_g1_camms.repository.IOrderDetailRepository;
import com.example.swp493_g1_camms.repository.IProductRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListExportOrderResponse {
    //order_code is order_id
    private Long order_code;
    private LocalDateTime importDate;
    private LocalDateTime exportDate;
    private Long user_id;
    private String username;
    private Long status;
    private String status_name;
    private double total_price;
    @Autowired
    IOrderDetailRepository orderDetailRepository;
    @Autowired
    IConsignmentProductRepository consignmentProductRepository;
    @Autowired
    IProductRepository IProductRepository;

    public static List<ListExportOrderResponse> createSuccessData(List<Order> orderList){
        List<ListExportOrderResponse> listExportOrderResponses = new ArrayList<>();
        for (Order o: orderList
             ) {
            ListExportOrderResponse exportOrderResponse = new ListExportOrderResponse();
            exportOrderResponse.order_code = o.getId();
            System.out.println("order code la:"+  exportOrderResponse.order_code);
            exportOrderResponse.importDate = o.getCreatedDate();
            System.out.println("order import date la:"+  exportOrderResponse.importDate);
            exportOrderResponse.exportDate = o.getConfirmDate();
            System.out.println("order export date la:"+  exportOrderResponse.exportDate);
            exportOrderResponse.user_id = o.getUser().getId();
            System.out.println("user id la:"+  exportOrderResponse.user_id);
            exportOrderResponse.username = o.getUser().getUsername();
            System.out.println("user name la:"+  exportOrderResponse.username);
            exportOrderResponse.status = o.getStatus().getId();
            System.out.println("status id la:"+  exportOrderResponse.status);
            exportOrderResponse.status_name = o.getStatus().getName();
            System.out.println("status name la:"+  exportOrderResponse.status_name);
            exportOrderResponse.total_price = exportOrderResponse.getTotalPrice(o.getId());
            System.out.println("total price la:"+  exportOrderResponse.total_price);
            listExportOrderResponses.add(exportOrderResponse);
        }
        return listExportOrderResponses;
    }

    public Double getTotalPrice(Long order_id){
        List<OrderDetail> orderDetailList = orderDetailRepository.getAllOrderDetailByOrderId(order_id);
        System.out.println("order datail gom co:"+orderDetailList.size());
        List<ConsignmentProduct> consignment_id_list = new ArrayList<>();
        for (OrderDetail od:orderDetailList
             ) {
            ConsignmentProduct consignmentProduct =
                    consignmentProductRepository.getConsignmentProductByConsignmentId(od.getConsignment().getId());
            consignment_id_list.add(consignmentProduct);
            System.out.println("consignment ");
        }

        double totalPrice = 0;
        for (ConsignmentProduct cp:consignment_id_list
             ) {
//            Product p = IProductRepository.findProductById(cp.getProduct().getId());
            totalPrice += cp.getProduct().getUnitprice()*cp.getQuantity();
        }
        return totalPrice;
    }
}
