package com.example.myspringbootplayground.payment;

import com.example.myspringbootplayground.account.AccountService;
import com.example.myspringbootplayground.coin.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void transfer(Long senderId, Long receiverId, Long amount) {
        logTransaction(senderId, receiverId, amount);
        // This service cause a problem when the app throw exception after this service committed.
        accountService.increaseBalance(receiverId, amount);
        // This service cause a problem when the app throw exception after this service committed.
        accountService.decreaseBalance(senderId, amount);

        try {
            Thread.sleep(10000);
        } catch (Exception e) {}

        coinService.increaseCoin(senderId, amount / 10);
        coinService.increaseCoin(receiverId, amount / 100);
    }

    @Transactional
    public void logTransaction(Long senderId, Long receiverId, Long amount) {
        paymentRepository.logTransaction(senderId, receiverId, amount);
    }
}
