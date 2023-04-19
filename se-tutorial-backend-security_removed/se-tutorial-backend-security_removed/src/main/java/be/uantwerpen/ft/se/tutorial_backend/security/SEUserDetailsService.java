package be.uantwerpen.ft.se.tutorial_backend.security;

import be.uantwerpen.ft.se.tutorial_backend.model.users.Privilege;
import be.uantwerpen.ft.se.tutorial_backend.model.users.User;
import be.uantwerpen.ft.se.tutorial_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SEUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override // This method is called by Spring Security
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userRes = userRepository.findByEmail(email);
        if (userRes.isEmpty())
            throw new UsernameNotFoundException("could not find User with email: " + email);
            User user = userRes.get();
            // get list of user priviliges
            Set<Privilege> privileges = user.getRoles().stream()
                    .flatMap(role -> role.getPrivileges().stream())
                    .collect(Collectors.toSet());
            Set<SimpleGrantedAuthority> grantedAuthorities = privileges.stream()
                    .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))
                    .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                email, user.getPassword(), grantedAuthorities);
    }
}
//This is a class that Spring Security uses to load user details, such as their email and password.
// It is marked with the @Component annotation, which means that Spring will create an instance of it and
// manage its lifecycle.
//
//The SEUserDetailsService implements the UserDetailsService interface, which has a single method called
// loadUserByUsername. This method is called by Spring Security when it needs to retrieve user details for
// authentication purposes.
//
//The loadUserByUsername method takes an email as an argument and returns a UserDetails object.
// The method first checks if there is a user with the given email in the database. If there is no such user,
// it throws a UsernameNotFoundException. If the user is found, the method gets
// a list of their privileges and creates a set of SimpleGrantedAuthority objects based on those privileges.
// The SimpleGrantedAuthority objects represent the user's roles and permissions.
//
//Finally, the method returns a org.springframework.security.core.userdetails.User object,
// which implements the UserDetails interface. The User object contains the user's email, password, and
// authorities. The authorities represent the user's roles and permissions, and
// they are used by Spring Security to determine if the user has access to specific resources in the application.