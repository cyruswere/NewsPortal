package dao;

import models.Department;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oNewsDao implements NewsDao{
    private final Sql2o sql2o;
    public Sql2oNewsDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }


    @Override
    public void add(News news) {
        String sql = "INSERT INTO news (title,content,authorid) VALUES (:title ,:content,:authorId)"; //if you change your model, be sure to update here as well!
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }



    @Override
    public List<News> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news")
                    .executeAndFetch(News.class);
        }
    }



    @Override
    public void addNewsToADepartment(News news, Department department) {
        String sql = "INSERT INTO news_depatments (newsid, departmentid) VALUES (:newsid, :departmentid)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("newsid", news.getId())
                    .addParameter("departmentid", department.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public List<Department> getAllDepartmentsForNews(int newsid) {
        List<Department> departments= new ArrayList();
        String joinQuery = "SELECT departmentid FROM news_depatments WHERE newsid = :newsid";

        try (Connection con = sql2o.open()) {
            List<Integer> allDepartmentIds = con.createQuery(joinQuery)
                    .addParameter("newsid", newsid)
                    .executeAndFetch(Integer.class);
            for (Integer depId : allDepartmentIds){
                String departmentTypeQuery = "SELECT * FROM departments WHERE id = :departmentid";
                departments.add(
                        con.createQuery(departmentTypeQuery)
                                .addParameter("departmentid", depId)
                                .executeAndFetchFirst(Department.class));
            }
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return departments;
    }

    @Override
    public News findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM news WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(News.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from news WHERE id = :id";
        String deleteJoin = "DELETE from news_depatments WHERE newsid = :newsid";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("newsid", id)
                    .executeUpdate();

        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAll() {
        String sql = "DELETE from news";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

}
