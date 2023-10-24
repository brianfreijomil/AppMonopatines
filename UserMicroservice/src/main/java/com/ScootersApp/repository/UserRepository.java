package com.ScootersApp.repository;

import com.ScootersApp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByMailAndPassword(String e, String p);

    public User findByMail(String mail);

    public User findByID(Long id);
}
