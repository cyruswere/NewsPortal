package dao;


import models.Department;
import models.News;

import java.util.List;

public interface NewsDao {
    //create
    void add(News news);
    void addNewsToADepartment(News news, Department department);
    News findById(int id);

    //read
    List<News> getAll();
    List<Department> getAllDepartmentsForNews(int id);

    //update
    //omit for now

    //delete
    void deleteById(int id);

    void clearAll();

}
