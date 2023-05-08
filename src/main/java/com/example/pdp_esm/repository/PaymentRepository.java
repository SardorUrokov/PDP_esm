package com.example.pdp_esm.repository;

import com.example.pdp_esm.entity.Payment;
import com.example.pdp_esm.entity.Student;
import com.example.pdp_esm.entity.enums.PayType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByAmountOrStudentOrPayTypeOrCreatedAt(Double amount, Student student, PayType payType, Date createdAt);
}
