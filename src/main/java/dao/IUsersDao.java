package dao;

import models.Department;
import models.News;
import models.User;

import java.util.List;

public interface IUsersDao {
    //create
    void add(User user);
    void addUserNewsDepartment(User user, News news, Department department);
    //read
    List<User> getAll();

    // find by ID
    User findById(int id);
    List<Department> getUserDepartment(int blackout_id);
    List<User> getUserNews(int blackout_id);
    //update


    //delete
    void deleteById(int id);
    void clearAll();
}
