package com.zikozee.springboot.mvcblog.repositories;

import com.zikozee.springboot.mvcblog.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
