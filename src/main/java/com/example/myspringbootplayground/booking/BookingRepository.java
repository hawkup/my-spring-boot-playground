package com.example.myspringbootplayground.booking;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    @Query(value = "select * from booking b where b.id = :id FOR UPDATE", nativeQuery = true)
    Optional<Booking> findByIdLock(@Param("id") Long id);
}
