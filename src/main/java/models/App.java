import com.google.gson.Gson;
import exceptions.ApiException;
import dao.Sql2oDepartmentDao;
import dao.Sql2oNewsDao;
import dao.Sql2oUserDao;
import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

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
        staticFileLocation("/public");

        Sql2oDepartmentDao departmentDao;
        Sql2oNewsDao newsDao;
        Sql2oUserDao userDao;

        Connection conn;
        Gson gson = new Gson();

//        String connectionString = "jdbc:postgresql://localhost:5432/portal";
//        Sql2o sql2o = new Sql2o(connectionString,  "moringa", "kidero");


        String connectionString = "jdbc:postgresql://ec2-54-227-246-76.compute-1.amazonaws.com:5432/d310b8gc46ca3r"; //!
        Sql2o sql2o = new Sql2o(connectionString, "lnsuwcuiafiagf", "bf0dfafd9468bb8ba8d0385a985f8fbb43d291d72ccae19a1b885a149bd4c73a"); //!


        departmentDao = new Sql2oDepartmentDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open();

        //Home url
        get("/", "application/json", (req, res) -> {
            String welcome = "Welcome to the news portal";
            res.status(201);
            return gson.toJson(welcome);
        });


        //End points for departments

        //Create Department
        post("/departments/new", "application/json", (req, res) -> {
            Department department = gson.fromJson(req.body(), Department.class);
            departmentDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        //Read all departments
        get("/departments", "application/json", (req, res) -> { //accept a request in format JSON from an app
            return gson.toJson(departmentDao.getAll());//send it back to be displayed
        });

        //Get department by Id
        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else {
                return gson.toJson(departmentToFind);
            }


        });

        //Get all news by a specific department.
        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            Department departmentToFind = departmentDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            else if (departmentDao.getAllNewsForADepartment(departmentId).size()==0){
                return "{\"message\":\"I'm sorry, but no news briefing are listed for this department.\"}";
            }
            else {
                return gson.toJson(departmentDao.getAllNewsForADepartment(departmentId));
            }
        });


        //End points for users
        //Create User
        post("/users/new", "application/json", (req, res) -> {
            User user = gson.fromJson(req.body(), User.class);
            userDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        //Get All existing Users
        get("/users", "application/json", (req, res) -> { //accept a request in format JSON from an app
            return gson.toJson(userDao.getAll());//send it back to be displayed
        });

        //Get user by Id.
        get("/users/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int userId = Integer.parseInt(req.params("id"));
            User userToFind = userDao.findById(userId);
            if (userToFind == null){
                throw new ApiException(404, String.format("No User with the id: \"%s\" exists", req.params("id")));
            }
            else {
                return gson.toJson(userToFind);
            }
        });


        //End points for news

        //Create news
        post("/news/new", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });

        //Get all news
        get("/news", "application/json", (req, res) -> { //accept a request in format JSON from an app
            return gson.toJson(newsDao.getAll());//send it back to be displayed
        });

        //Get News by Id
        get("/news/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            res.type("application/json");
            int newsId = Integer.parseInt(req.params("id"));
            News newsToFind = newsDao.findById(newsId);
            if (newsToFind == null){
                throw new ApiException(404, String.format("No News Briefing with the id: \"%s\" exists", req.params("id")));
            }
            else {
                return gson.toJson(newsToFind);
            }
        });

        //Add a department to news.
        post("/news/:newsId/departments/:departmentId", "application/json", (req, res) -> {
            int newsId = Integer.parseInt(req.params("newsId"));
            int departmentId = Integer.parseInt(req.params("departmentId"));
            News news = newsDao.findById(newsId);
            Department department = departmentDao.findById(departmentId);

            if (news != null && department != null){
                departmentDao.addDepartmentToNews(department,news);
                res.status(201);
                return gson.toJson(String.format("News Briefing '%s' and department '%s' have been associated",department.getName(), news.getTitle()));
            }
            else {
                throw new ApiException(404, String.format("News Briefing or Department does not exist"));
            }
        });

        //Get news based on a department
        get("/news/:id/departments", "application/json", (req, res) -> {
            int newsId = Integer.parseInt(req.params("id"));
            News newsToFind = newsDao.findById(newsId);

            if (newsToFind == null){
                throw new ApiException(404, String.format("No news briefing with the id: \"%s\" exists", req.params("id")));
            }
            else if ( newsDao.getAllDepartmentsForNews(newsId).size()==0){
                return "{\"message\":\"I'm sorry, but no departments are listed for this news briefing.\"}";
            }
            else {
                return gson.toJson(newsDao.getAllDepartmentsForNews(newsId));
            }
        });



        //FILTERS
        exception(ApiException.class, (exception, req, res) -> {
            ApiException err = exception;
            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("status", err.getStatusCode());
            jsonMap.put("errorMessage", err.getMessage());
            res.type("application/json");
            res.status(err.getStatusCode());
            res.body(gson.toJson(jsonMap));
        });


        after((req, res) ->{
            res.type("application/json");
        });

    }
}
