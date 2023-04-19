package be.uantwerpen.ft.se.tutorial_backend.controller;

import be.uantwerpen.ft.se.tutorial_backend.model.users.User;
import be.uantwerpen.ft.se.tutorial_backend.model.users.dto.UserEditDto;
import be.uantwerpen.ft.se.tutorial_backend.model.users.dto.UserGetDto;
import be.uantwerpen.ft.se.tutorial_backend.repository.UserRepository;
import be.uantwerpen.ft.se.tutorial_backend.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private final ModelMapper modelmapper = new ModelMapper();

    Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping
    @PreAuthorize("hasAuthority('admin_read')")
    public Iterable<UserGetDto> getUsers() {
        logger.info("GET: /users");
        return ((List<User>)userRepository.findAll())
                .stream()
                .map(user -> modelmapper.map(user, UserGetDto.class))
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin_read')")
    public UserGetDto getUserById(@PathVariable Long id) {
        logger.info("GET: /users/"+id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return modelmapper.map(user, UserGetDto.class);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('admin_write')")
    public void updateUser(@PathVariable Long id,@Valid @RequestBody UserEditDto user) throws ResponseStatusException {
        logger.info("POST: /users/"+id);
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.save(userService.updateUserInformation(existingUser, user));
    }

    @PutMapping("/new")
    @PreAuthorize("hasAuthority('admin_write')")
    public void createUser(@Valid @RequestBody UserEditDto user) throws ResponseStatusException {
        logger.info("PUT: /users/new");
        User new_user = new User();
        userRepository.save(userService.updateUserInformation(new_user, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin_write')")
    public void deleteUser(@PathVariable Long id) {
        logger.info("DELETE: /users/"+id);
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
