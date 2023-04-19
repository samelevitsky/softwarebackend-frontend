package be.uantwerpen.ft.se.tutorial_backend.repository;

import be.uantwerpen.ft.se.tutorial_backend.model.users.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
