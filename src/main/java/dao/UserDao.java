package dao;

import models.User;

import java.util.List;

public interface UserDao {

    //create
    void add(User user);
    User findById(int id);

    //void addUserToADepartment(User user, Department department);

    //read
    List<User> getAll();

    // List<Department> getAllDepartmentsForAUser(int id);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}
