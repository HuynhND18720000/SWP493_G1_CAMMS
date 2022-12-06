package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.ResetPassHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IResetPassHistoryRepository extends JpaRepository<ResetPassHistory, Long> {

    @Query("select rph.otp_code from ResetPassHistory as rph where rph.otp_code =?1 and rph.status = false")
    String getOTPExist(String otp);

    @Override
    Optional<ResetPassHistory> findById(Long aLong);

    @Query("select rph from ResetPassHistory as rph where rph.otp_code =?1")
    ResetPassHistory getUserByOtpCode(String otp);

    @Query("select rph from ResetPassHistory rph where rph.user.id = ?1 " +
            "and NULLIF(rph.old_password, ' ') IS NOT NULL ")
    List<ResetPassHistory> getUserByUserId(Long user_Id);
}
