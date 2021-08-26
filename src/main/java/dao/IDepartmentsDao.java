package dao;

import models.Department;
import models.News;
import models.User;

import java.util.List;

public interface IDepartmentsDao {
    //create
    void add(Department departments);
    void addDepartmentNewsUser(User user, News news, Department department);

    //read
    List<Department> getAll();

    // find by ID
    Department findById(int id);
    List<User> getDepartmentUser(int blackout_id);
    List<News> getDepartmentNews(int blackout_id);
    //update


    //delete
    void deleteById(int id);
    void clearAll();
}
