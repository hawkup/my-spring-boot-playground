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
    public void transfer(Long senderId, Long receiverId, Long amount) throws InterruptedException {
        boolean isSuccess = false;
        try {
            logTransaction(senderId, receiverId, amount);

            isSuccess = accountService.transferBalance(senderId, receiverId, amount);

            Thread.sleep(10000);
            coinService.increaseCoin(senderId, amount / 10);
            coinService.increaseCoin(receiverId, amount / 100);
        } catch (Exception e) {
            // Manual rollback
            if (isSuccess) {
                accountService.transferBalance(receiverId, senderId, amount);
            }
            throw e;
        }
    }

    @Transactional
    public void logTransaction(Long senderId, Long receiverId, Long amount) {
        paymentRepository.logTransaction(senderId, receiverId, amount);
    }
}
