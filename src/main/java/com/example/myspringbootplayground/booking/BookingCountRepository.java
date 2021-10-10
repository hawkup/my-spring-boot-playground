package com.example.myspringbootplayground.booking;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingCountRepository extends CrudRepository<BookingCount, Long> {
    @Query(value = "select * from booking_count bc where bc.booking_id = :bookingId FOR UPDATE", nativeQuery = true)
    Optional<BookingCount> findByBookingIdLock(@Param("bookingId") Long bookingId);
}
