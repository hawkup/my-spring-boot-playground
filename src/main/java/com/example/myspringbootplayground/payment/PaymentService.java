package com.example.myspringbootplayground.payment;

import com.example.myspringbootplayground.account.AccountService;
import com.example.myspringbootplayground.coin.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private CoinService coinService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void transferBalance(Long senderId, Long receiverId, Long amount) {
        accountService.increaseBalance(receiverId, amount);
        accountService.decreaseBalance(senderId, amount);
    }

    @Transactional
    public void transfer(Long senderId, Long receiverId, Long amount) {
        logTransaction(senderId, receiverId, amount);

        transferBalance(senderId, receiverId, amount);

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
