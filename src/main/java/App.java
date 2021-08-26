import com.google.gson.Gson;
import dao.Sql2oDepartmentsDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUsersDao;
import exceptions.ApiException;
import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {

        port(getHerokuAssignedPort());

        Connection conn;
        Sql2oNewsDao newsDao;
        Sql2oUsersDao userDao;
        Sql2oDepartmentsDao departmentDao;
//        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/news_portal",  "sirkadima", "kadima123");
        Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-54-147-93-73.compute-1.amazonaws.com:5432/d724gmkjp7qlso",
                "fmyplprxmecilw", "455a8046dee64b8f2ea283a2554ae4c3ee1589c2e50720419e9a314788fc688d");


        userDao = new Sql2oUsersDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentsDao(sql2o);

        conn = sql2o.open();
        Gson gson = new Gson();

        //CREATE nEW(aDD uSER)
        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //GET ALL
        get("/users", "application/json", (req, res) -> {
            return gson.toJson(userDao.getAll());
        });

        //GET USER BY ID
        get("/users/:id", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("id"));
            User userToFind = userDao.findById(userId);
            return gson.toJson(userToFind);
        });

        //DELETE BY ID
        delete("users/:user_id", (req, res) -> {
            int user_id = Integer.parseInt(req.params("user_id"));
            User userToDelete = userDao.findById(user_id);
            userDao.deleteById(user_id);
            return gson.toJson(userToDelete);
        });

        //CREATE nEW(aDD DEPARTMENT)
        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //GET ALL
        get("/departments", "application/json", (req, res) -> {
            return gson.toJson(departmentDao.getAll());
        });

        //GET Department BY ID
        get("/departments/:id", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            return gson.toJson(departmentToFind);
        });

        //DELETE BY ID
        delete("departments/:department_id", (req, res) -> {
            int department_id = Integer.parseInt(req.params("department_id"));
            Department departmentToDelete = departmentDao.findById(department_id);
            departmentDao.deleteById(department_id);
            return gson.toJson(departmentToDelete);
        });

//        ADD ROUTING FOR NEWS

        //CREATE NEWS POST
        post("/news/new", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        //GET ALL news
        get("/news", "application/json", (req, res) -> {
            return gson.toJson(newsDao.getAll());
        });

        //GET news BY ID
        get("/news/:id", "application/json", (req, res) -> {
            int newsId = Integer.parseInt(req.params("id"));
            News newsToFind = newsDao.findById(newsId);
            return gson.toJson(newsToFind);
        });

        //DELETE BY ID
        delete("news/:news_id", (req, res) -> {
            int news_id = Integer.parseInt(req.params("news_id"));
            News newsToDelete = newsDao.findById(news_id);
            newsDao.deleteById(news_id);
            return gson.toJson(newsToDelete);
        });
    }
}
