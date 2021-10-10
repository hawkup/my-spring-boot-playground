package com.example.myspringbootplayground.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping
    public Booking makeBook() {
        return bookingService.makeBook();
    }

    @PostMapping("{id}/confirm")
    public Booking confirmBook(@PathVariable Long id) throws InterruptedException {
        return bookingService.confirmBook(id);
    }
}
