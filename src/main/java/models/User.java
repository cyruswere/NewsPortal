package models;

import java.util.ArrayList;
import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String title;
    private String roles;
    private String associated_department;


    public User(String name, String title, String roles, String associated_department) {
        this.name = name;
        this.title = title;
        this.roles = roles;
        this.associated_department = associated_department;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAssociated_department() {
        return associated_department;
    }

    public void setAssociated_department(String associated_department) {
        this.associated_department = associated_department;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && name.equals(user.name) && title.equals(user.title) && roles.equals(user.roles) && associated_department.equals(user.associated_department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title, roles, associated_department);
    }

}
