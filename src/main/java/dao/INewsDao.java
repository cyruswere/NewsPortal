package dao;

import models.Department;
import models.News;
import models.User;

import java.util.List;

public interface INewsDao {

    //create
    void add(News news);
    void addNewsUserDepartment(User user, News news, Department department);

    //read
    List<News> getAll();

    // find by ID
    News findById(int id);
    List<User> getNewsUser(int blackout_id);
    List<Department> getNewsDepartment(int blackout_id);

    //update


    //delete
    void deleteById(int id);
    void clearAll();
}
