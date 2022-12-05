package com.example.swp493_g1_camms.services.interfaceService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

public interface ValidCheckService {
    public void validDate(LocalDateTime dateFrom1, LocalDateTime dateTo1, String dateFrom, String dateTo );
    public boolean isEmailManufacturerExist(String email);
}
