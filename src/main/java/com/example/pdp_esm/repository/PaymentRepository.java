package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Payment;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.entity.enums.PayStatus;
import com.example.pdp_esm.entity.enums.PayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByAmountOrStudentOrPayTypeOrCreatedAt(Double amount, Student student, PayType payType, Date createdAt);

    @Query("SELECT p FROM Payment p WHERE p.createdAt >= :fromTime AND p.createdAt < :toTime")
    List<Payment> findPaymentsWithinInterval(@Param("fromTime") Date fromTime, @Param("toTime") Date toTime);

    List<Payment> findAllByPayStatus(PayStatus payStatus);
}
