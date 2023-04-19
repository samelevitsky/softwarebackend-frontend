package be.uantwerpen.ft.se.tutorial_backend.model.users.dto;

public class RoleOptionDto {
    private long id;
    private String name;

    public RoleOptionDto() {
    }

    public RoleOptionDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
