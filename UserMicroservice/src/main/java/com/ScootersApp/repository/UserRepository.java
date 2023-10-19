package com.ScootersApp.repository;

import com.ScootersApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByMail(String e);
}
