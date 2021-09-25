package com.example.myspringbootplayground.account;

import com.example.myspringbootplayground.account.request.CreateAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/accounts")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest.getUserId(), createAccountRequest.getBalance());
    }
}
