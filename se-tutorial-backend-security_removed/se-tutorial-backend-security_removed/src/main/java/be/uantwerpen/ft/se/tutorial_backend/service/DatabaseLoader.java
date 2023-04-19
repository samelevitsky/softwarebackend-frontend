package be.uantwerpen.ft.se.tutorial_backend.service;

import be.uantwerpen.ft.se.tutorial_backend.model.users.Privilege;
import be.uantwerpen.ft.se.tutorial_backend.model.users.Role;
import be.uantwerpen.ft.se.tutorial_backend.model.users.User;
import be.uantwerpen.ft.se.tutorial_backend.repository.PrivilegeRepository;
import be.uantwerpen.ft.se.tutorial_backend.repository.RoleRepository;
import be.uantwerpen.ft.se.tutorial_backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Profile("dev")
public class DatabaseLoader {
    @Autowired
    final private UserRepository userRepository;

    @Autowired
    final private PrivilegeRepository privilegeRepository;

    @Autowired
    final private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatabaseLoader(UserRepository userRepository, PrivilegeRepository privilegeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void initDatabase() {
        // Create privileges
        Privilege p1 = new Privilege("logon");
        privilegeRepository.save(p1);
        Privilege p2 = new Privilege("admin_read");
        privilegeRepository.save(p2);
        Privilege p3 = new Privilege("admin_write");
        privilegeRepository.save(p3);
        Privilege p4 = new Privilege("roles_read");
        privilegeRepository.save(p4);

        // Create roles
        Role administrator = new Role("Administrator");
        Role tester = new Role("Tester");
        List<Privilege> privileges = new ArrayList<>();
        privileges.add(p1);
        tester.setPrivileges(privileges);
        roleRepository.save(tester);
        privileges = new ArrayList<>();
        privileges.add(p1);
        privileges.add(p2);
        privileges.add(p3);
        privileges.add(p4);
        administrator.setPrivileges(privileges);
        roleRepository.save(administrator);

        // Create users
        User u1 = new User("john.doe@uantwerpen.be", passwordEncoder.encode("password"));
        u1.setFirstName("John");
        u1.setLastName("Doe");
        u1.setLocation("Antwerp");
        u1.setTelephone("123456789");
        u1.setRoles(Set.of(administrator));
        User u2 = new User("dieter.balemans@uantwerpen.be", passwordEncoder.encode("password"));
        u2.setFirstName("Dieter");
        u2.setLastName("Balemans");
        u2.setLocation("Antwerp");
        u2.setTelephone("123456789");
        u2.setRoles(Set.of(tester));
        userRepository.save(u1);
        userRepository.save(u2);

        for(int i = 0; i < 100; i++) {
            User u = new User("user" + i + "@uantwerpen.be", passwordEncoder.encode("password"));
            u.setFirstName("User");
            u.setLastName("Number " + i);
            u.setLocation("Antwerp");
            u.setTelephone("123456789");
            u.setRoles(Set.of(tester));
            userRepository.save(u);
        }
    }
}
