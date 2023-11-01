package com.scooter.apiGateway.repository;

import com.scooter.apiGateway.DTO.UserDto;
import com.scooter.apiGateway.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u.mail, u.password, u.roles from User u where u.mail = :mail")
    UserDto getUser(String mail);
}
