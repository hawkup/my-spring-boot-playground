package com.example.myspringbootplayground.coin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CoinRepository extends CrudRepository<Coin, Long> {
    @Query(value = "select * from coin c where c.user_id = :userId FOR UPDATE", nativeQuery = true)
    Optional<Coin> findByUserIdLock(@Param("userId") Long userId);
}
