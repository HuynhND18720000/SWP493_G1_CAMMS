package com.example.swp493_g1_camms.repository;

import com.example.swp493_g1_camms.entities.Consignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IConsignmentRepository extends JpaRepository<Consignment,Long> {
    @Query(value = "SELECT MAX(c.consignment_code) AS lastConsignment FROM Consignment AS c")
    Long getLastConsignmentCode();

    @Query(value = "SELECT c FROM Consignment AS c where c.consignment_code = ?1")
    Consignment getCurrentConsignmentId(Long consignment_code);

    //dung cho import order
    @Query(value = "SELECT c FROM Consignment as c where c.consignment_code = ?1")
    Consignment getConsignmentByConsignmentCode(Long consignment_code);

    //dung cho export order
    @Query(value = "SELECT c FROM Consignment as c where c.consignment_code = ?1")
    List<Consignment> getListConsignmentExportByConsignmentCode(Long consignment_code);


    //lay ra nhieu consigment dua tren listId
    @Query("SELECT c FROM Consignment c " + "Where c.id IN (?1)  AND c.deletedAt = false" )
    List<Consignment> findAllConsignmentByListId(List<Long> listConsignmentId);


}
