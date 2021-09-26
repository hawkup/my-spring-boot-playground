package com.example.myspringbootplayground.coin;

import com.example.myspringbootplayground.user.User;
import com.example.myspringbootplayground.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CoinService {
    @Autowired
    private CoinRepository coinRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    private Coin getOrCreateCoin(Long userId) {
        Optional<Coin> coin = coinRepository.findByUserIdLock(userId);

        if (coin.isEmpty()) {
            User user = userRepository.findById(userId).orElseThrow();
            Coin newCoin = new Coin();
            newCoin.setUser(user);
            newCoin.setBalance(0L);
            return coinRepository.save(newCoin);
        }

        return coin.get();
    }

    @Transactional
    public void increaseCoin(Long userId, Long amount) {
        if (amount <= 0) {
            throw new RuntimeException();
        }

        Coin coin = getOrCreateCoin(userId);
        Long balance = coin.getBalance();
        coin.setBalance(balance + amount);
        coinRepository.save(coin);
    }

    @Transactional
    public void decreaseCoin(Long userId,  Long amount) {
        if (amount <= 0) {
            throw new RuntimeException();
        }

        Coin coin = getOrCreateCoin(userId);
        Long balance = coin.getBalance();
        coin.setBalance(balance - amount);
        coinRepository.save(coin);
    }
}
