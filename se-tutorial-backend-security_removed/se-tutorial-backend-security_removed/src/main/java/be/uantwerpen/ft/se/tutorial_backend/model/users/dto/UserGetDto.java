package be.uantwerpen.ft.se.tutorial_backend.model.users.dto;

import java.util.List;

public class UserGetDto {
    long id;
    String firstName;
    String lastName;
    String email;
    String uaNumber;
    String telephone;
    String location;
    List<RoleOptionDto> roles;

    public UserGetDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public List<RoleOptionDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleOptionDto> roles) {
        this.roles = roles;
    }
}
