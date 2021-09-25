package com.example.myspringbootplayground.payment;

import com.example.myspringbootplayground.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public void transfer(Long senderId, Long receiverId, Long amount) {
        logTransaction(senderId, receiverId, amount);
        accountService.decreaseBalance(senderId, amount);
        accountService.increaseBalance(receiverId, amount);
    }

    @Transactional
    public void logTransaction(Long senderId, Long receiverId, Long amount) {
        paymentRepository.logTransaction(senderId, receiverId, amount);
    }
}
