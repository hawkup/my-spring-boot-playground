package com.example.myspringbootplayground.payment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    @Modifying
    @Query(value = "INSERT INTO payment (sender_id, receiver_id, amount, created_at) VALUES (:senderId, :receiverId, :amount, UTC_TIMESTAMP)", nativeQuery = true)
    void logTransaction(@Param("senderId") Long senderId, @Param("receiverId") Long receiverId, @Param("amount") Long amount);
}
