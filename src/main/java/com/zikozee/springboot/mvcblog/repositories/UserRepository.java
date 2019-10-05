package com.zikozee.springboot.mvcblog.repositories;

import com.zikozee.springboot.mvcblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
