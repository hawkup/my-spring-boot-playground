package com.example.myspringbootplayground.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingCountRepository bookingCountRepository;

    @Transactional
    public Booking makeBook() {
        Booking booking = new Booking();
        booking.setStatus(BookingStatus.DRAFT);
        booking = bookingRepository.save(booking);

        BookingCount bookingCount = new BookingCount();
        bookingCount.setBooking(booking);
        bookingCountRepository.save(bookingCount);

        return booking;
    }

    @Transactional
    public Booking confirmBook(Long id) throws InterruptedException {
        Booking booking = bookingRepository.findById(id).orElseThrow();

        if (booking.getStatus().equals(BookingStatus.DRAFT)) {
            BookingCount bookingCount = bookingCountRepository.findByBookingIdLock(id).orElseThrow();

            Thread.sleep(5000);

            bookingCount.setCount(bookingCount.getCount() + 1L);

            bookingCountRepository.save(bookingCount);

            booking = bookingRepository.findByIdLock(id).orElseThrow();
            booking.setStatus(BookingStatus.BOOK);
            booking = bookingRepository.save(booking);
        }

        return booking;
    }
}
