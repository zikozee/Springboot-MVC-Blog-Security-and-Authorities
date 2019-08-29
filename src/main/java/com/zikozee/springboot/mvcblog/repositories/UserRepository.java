package com.zikozee.springboot.mvcblog.repositories;

import com.zikozee.springboot.mvcblog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
