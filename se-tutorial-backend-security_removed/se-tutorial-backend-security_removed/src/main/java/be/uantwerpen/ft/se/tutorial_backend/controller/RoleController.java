package be.uantwerpen.ft.se.tutorial_backend.controller;

import be.uantwerpen.ft.se.tutorial_backend.model.users.Role;
import be.uantwerpen.ft.se.tutorial_backend.model.users.dto.RoleOptionDto;
import be.uantwerpen.ft.se.tutorial_backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    Logger logger = Logger.getLogger(RoleController.class.getName());

    @GetMapping
    @PreAuthorize("hasAuthority('roles_read')")
    public List<RoleOptionDto> getRoles() {
        logger.info("GET: /roles");
        return ((List<Role>) roleRepository.findAll())
                .stream()
                .map(role -> new RoleOptionDto(role.getId(), role.getName()))
                .toList();
    }
}
