package com.example.myspringbootplayground.account;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query(value = "select * from account a where a.user_id = :userId FOR UPDATE", nativeQuery = true)
    Optional<Account> findByUserIdLock(@Param("userId") Long userId);

    Optional<Account> findByUserId(Long userId);
}
