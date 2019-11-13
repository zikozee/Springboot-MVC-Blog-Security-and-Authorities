package com.zikozee.springboot.mvcblog.repositories;

import com.zikozee.springboot.mvcblog.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p left join fetch p.author order by p.date desc ")
    List<Post> findLatest5Posts(Pageable pageable);

    Set<Post> findByOrderByDateAsc();
}
