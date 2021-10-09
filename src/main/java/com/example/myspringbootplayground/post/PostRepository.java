package com.example.myspringbootplayground.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Query(value = "select * from post p where p.id = :id FOR UPDATE", nativeQuery = true)
    Optional<Post> findByIdLock(@Param("id") Long id);
}
