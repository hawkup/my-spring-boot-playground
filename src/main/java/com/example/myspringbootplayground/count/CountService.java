package com.example.myspringbootplayground.count;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountService {
    @Autowired
    private CountRepository countRepository;

    @Transactional
    public Count create() {
        Count count = new Count();
        count.setAmount(500L);
        count = countRepository.save(count);
        return count;
    }

    @Transactional
    public Count decreaseLock(Long id) throws InterruptedException {
        Count count = countRepository.findByCountIdLock(id).orElseThrow();
        Thread.sleep(500);
        Long amount = count.getAmount();
        count.setAmount(amount - 1);
        count = countRepository.save(count);

        return count;
    }
}
