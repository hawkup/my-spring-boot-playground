package com.example.myspringbootplayground.post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long> {
    @Query(value = "select * from tag t where t.post_id = :postId FOR UPDATE", nativeQuery = true)
    Optional<Tag> findByPostIdLock(@Param("postId") Long postId);

    Optional<Tag> findByPostId(@Param("postId") Long postId);
}
