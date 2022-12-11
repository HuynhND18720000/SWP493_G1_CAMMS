package com.example.swp493_g1_camms.payload.response;

import com.example.swp493_g1_camms.entities.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListStaffResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    private String image;

    private LocalDate dob;

    private String role;

    public static List<ListStaffResponse> createSuccessData(List<Staff> staffs) {
        List<ListStaffResponse> listStaffResponses = new ArrayList<>();
        for(Staff staff : staffs){
            ListStaffResponse listStaffResponse = new ListStaffResponse();
            listStaffResponse.id = staff.getId();
            listStaffResponse.fullName = staff.getFullName();
            listStaffResponse.email = staff.getEmail();
            listStaffResponse.phone = staff.getPhone();
            listStaffResponse.image = staff.getImage();
            listStaffResponse.dob = staff.getDob();
            listStaffResponse.role = staff.getRole();
            listStaffResponses.add(listStaffResponse);
        }
        return listStaffResponses;
    }
}
