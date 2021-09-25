package com.example.myspringbootplayground.account.request;

public class CreateAccountRequest {
    private Long userId;

    private Long balance;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
