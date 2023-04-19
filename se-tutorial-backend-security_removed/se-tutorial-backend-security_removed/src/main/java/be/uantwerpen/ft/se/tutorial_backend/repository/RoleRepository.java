package be.uantwerpen.ft.se.tutorial_backend.repository;

import be.uantwerpen.ft.se.tutorial_backend.model.users.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
