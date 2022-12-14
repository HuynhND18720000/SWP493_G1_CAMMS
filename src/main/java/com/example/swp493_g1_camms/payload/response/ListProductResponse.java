package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListProductResponse {
    private Long id;

    private String productCode;

    private String name;

    private String description;

    private String image;

    private Double unitprice;

    private LocalDateTime out_date;

    private Integer quantity;

    private String unitMeasure;
    private LocalDateTime import_date;
    private Long categoryId;
    private Long manufactorId;
    private String categoryName;
    private String manufactorName;
    public static List<ListProductResponse>  createSuccessData(List<Product> products){
        List<ListProductResponse> listProductResponses = new ArrayList<>();
        for (Product p: products
             ) {
            ListProductResponse  listProductResponse= new ListProductResponse();
            listProductResponse.id = p.getId();
            listProductResponse.productCode = p.getProductCode();
            listProductResponse.name = p.getName();
            listProductResponse.description = p.getDescription();
            listProductResponse.image=p.getImage();
            listProductResponse.unitprice=p.getUnitprice();
            listProductResponse.out_date = p.getOutDate();
            listProductResponse.quantity=p.getQuantity();
            listProductResponse.unitMeasure=p.getUnitMeasure();
            listProductResponses.add(listProductResponse);
        }
        return listProductResponses;
    }

    public static List<ListProductResponse> createProductData(List<Product> listProduct,
                                                              List<ProductResponse> lp){
        List<ListProductResponse> listProductResponses = new ArrayList<>();
        for (Product p:listProduct
             ) {
            for (ProductResponse p1: lp
                 ) {
                if(p.getId().equals(p1.getId())){
                    ListProductResponse listP = new ListProductResponse();
                    listP.setId(p.getId());
                    listP.setName(p.getName());
                    listP.setProductCode(p.getProductCode());
                    listP.setDescription(p.getDescription());
                    listP.setImage(p.getImage());
                    listP.setOut_date(p.getOutDate());
                    listP.setQuantity(p.getQuantity());
                    listP.setUnitMeasure(p.getUnitMeasure());
                    listP.setCategoryId(p.getCategory().getId());
                    listP.setManufactorId(p.getManufacturer().getId());
                    listP.setCategoryName(p.getCategory().getName());
                    listP.setManufactorName(p.getManufacturer().getName());
                    listP.setUnitprice(p.getUnitprice());
                    listProductResponses.add(listP);

                }
            }
        }
        return listProductResponses;
    }
}
