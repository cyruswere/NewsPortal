package models;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private int deptId;

    public User(String name, int deptId){
        this.name = name;
        this.deptId = deptId;
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

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && deptId == user.deptId && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, deptId);
    }
}
