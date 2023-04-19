package be.uantwerpen.ft.se.tutorial_backend.model.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserEditDto {
    String firstName;
    String lastName;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    String email;
    String uaNumber;
    String telephone;
    String location;
    String password;
    String repeatPassword;
    List<RoleOptionDto> roles;

    public UserEditDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUaNumber() {
        return uaNumber;
    }

    public void setUaNumber(String uaNumber) {
        this.uaNumber = uaNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public List<RoleOptionDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleOptionDto> roles) {
        this.roles = roles;
    }
}
