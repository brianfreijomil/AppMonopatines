package repository;

import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByE_mailAndPassword(String e, String p);
}
