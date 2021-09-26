package com.example.myspringbootplayground.account;

import com.example.myspringbootplayground.user.User;
import com.example.myspringbootplayground.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Account createAccount(Long userId, Long balance) {
        User user = userRepository.findById(userId).orElseThrow();

        Account account = new Account();
        account.setUser(user);
        account.setBalance(balance);

        account = accountRepository.save(account);
        return account;
    }

    public Account getAccount(Long userId) {
        Account account = accountRepository.findByUserId(userId).orElseThrow();
        return account;
    }

    @Transactional
    public Account increaseBalance(Long accountId, Long amount) {
        Account account = accountRepository.findByUserIdLock(accountId).orElseThrow();
        account.setBalance(account.getBalance() + amount);
        account = accountRepository.save(account);

        return account;
    }

    @Transactional
    public Account decreaseBalance(Long accountId, Long amount) {
        Account account = accountRepository.findByUserIdLock(accountId).orElseThrow();
        account.setBalance(account.getBalance() - amount);

        if (account.getBalance() < 0) {
            throw new RuntimeException();
        }

        account = accountRepository.save(account);
        return account;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean transferBalance(Long senderId, Long receiverId, Long amount) {
        increaseBalance(receiverId, amount);
        decreaseBalance(senderId, amount);

        return true;
    }
}
