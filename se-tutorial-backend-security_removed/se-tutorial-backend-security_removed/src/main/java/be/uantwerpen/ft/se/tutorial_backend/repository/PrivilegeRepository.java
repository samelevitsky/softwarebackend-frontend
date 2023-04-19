package be.uantwerpen.ft.se.tutorial_backend.repository;

import be.uantwerpen.ft.se.tutorial_backend.model.users.Privilege;
import org.springframework.data.repository.CrudRepository;

public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {
}
