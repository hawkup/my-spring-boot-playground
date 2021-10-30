package com.example.myspringbootplayground.count;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CountRepository extends CrudRepository<Count, Long> {
    @Query(value = "select * from count c where c.id = :countId FOR UPDATE", nativeQuery = true)
    Optional<Count> findByCountIdLock(@Param("countId") Long countId);
}
