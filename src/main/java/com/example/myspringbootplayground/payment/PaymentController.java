package com.example.myspringbootplayground.payment;

import com.example.myspringbootplayground.account.Account;
import com.example.myspringbootplayground.account.AccountService;
import com.example.myspringbootplayground.payment.request.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/payment")
public class PaymentController {
    @Autowired
    AccountService accountService;

    @Autowired
    PaymentService paymentService;

    @PostMapping(path = "/transfer")
    Account transfer(@RequestBody TransferRequest transferRequest) {
        paymentService.transfer(transferRequest.getSenderId(), transferRequest.getReceiverId(), transferRequest.getAmount());
        return accountService.getAccount(transferRequest.getSenderId());
    }
}
