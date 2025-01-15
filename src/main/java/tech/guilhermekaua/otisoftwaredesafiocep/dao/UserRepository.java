package tech.guilhermekaua.otisoftwaredesafiocep.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.guilhermekaua.otisoftwaredesafiocep.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT user FROM User user WHERE user.username = :username")
    Optional<User> findByUsername(String username);
}
