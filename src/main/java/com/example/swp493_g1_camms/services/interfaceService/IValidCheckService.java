package com.example.swp493_g1_camms.services.interfaceService;

import java.time.LocalDateTime;

public interface IValidCheckService {
    public LocalDateTime validDate(LocalDateTime date1, String dateFrom);
    public boolean isEmailManufacturerExist(String email);
}
