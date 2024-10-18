package edu.t1.chernykh.repository;

import edu.t1.chernykh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsUserByLogin(String login);
    Optional<User> findByLogin(String login);
}