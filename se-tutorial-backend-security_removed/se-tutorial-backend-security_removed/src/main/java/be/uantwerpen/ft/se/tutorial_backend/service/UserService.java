package be.uantwerpen.ft.se.tutorial_backend.service;

import be.uantwerpen.ft.se.tutorial_backend.model.users.Role;
import be.uantwerpen.ft.se.tutorial_backend.model.users.User;
import be.uantwerpen.ft.se.tutorial_backend.model.users.dto.UserEditDto;
import be.uantwerpen.ft.se.tutorial_backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    final private ModelMapper modelmapper = new ModelMapper();

    public User updateUserInformation(User oldUser, UserEditDto newUser) {
        if (newUser.getFirstName() != null) {
            oldUser.setFirstName(newUser.getFirstName());
        }
        if (newUser.getLastName() != null) {
            oldUser.setLastName(newUser.getLastName());
        }
        if (newUser.getEmail() != null && !newUser.getEmail().equals("")) {
            // Check if email is already in use
            if (userRepository.findByEmail(newUser.getEmail()).isPresent() && !oldUser.getEmail().equals(newUser.getEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
            }
            oldUser.setEmail(newUser.getEmail());
        }
        if (newUser.getUaNumber() != null) {
            oldUser.setUaNumber(newUser.getUaNumber());
        }
        if (newUser.getTelephone() != null) {
            oldUser.setTelephone(newUser.getTelephone());
        }
        if (newUser.getLocation() != null) {
            oldUser.setLocation(newUser.getLocation());
        }
        if (newUser.getPassword() != null) {
            if (!newUser.getPassword().equals(newUser.getRepeatPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
            }
            oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        if (newUser.getRoles() != null) {
            oldUser.setRoles(newUser.getRoles()
                    .stream()
                    .map(role -> modelmapper.map(role, Role.class))
                    .collect(Collectors.toSet()
                    ));
        }
        return oldUser;
    }

}
