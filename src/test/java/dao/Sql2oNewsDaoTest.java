package dao;

import models.Department;
import models.News;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Sql2oNewsDaoTest {

    private static Connection conn; //these variables are now static.
    private static Sql2oNewsDao newsDao;//these variables are now static.
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oUserDao userDao;

    @BeforeClass  //(run once before running any tests in this file)
    public static void setUp() throws Exception { //changed to static
        String connectionString = "jdbc:postgresql://localhost:5432/portal_test";
        Sql2o sql2o = new Sql2o(connectionString,  "moringa", "kidero");
        newsDao = new Sql2oNewsDao(sql2o);
        departmentDao = new Sql2oDepartmentDao(sql2o);
        userDao = new Sql2oUserDao(sql2o);
        conn = sql2o.open(); //open connection once before this test file is run
    }

    @After //run after every test
    public void tearDown() throws Exception {  //I have changed
        System.out.println("clearing database");
        newsDao.clearAll(); //clear all restaurants after every test
        departmentDao.clearAll(); //clear all restaurants after every test
        userDao.clearAll(); //clear all restaurants after every test
    }

    @AfterClass //changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception{ //changed to static
        conn.close(); // close connection once after this entire test file is finished
        System.out.println("connection closed");
    }

    @Test
    public void addingNewsSetsId() throws Exception {
        News newsOne = setupNews();
        assertEquals(1, newsOne.getId());
    }

    @Test
    public void getAll() throws Exception {
        News newsOne = setupNews();
        News newsTwo = setupNews();
        assertEquals(2, newsDao.getAll().size());
    }


    @Test
    public void deleteById() throws Exception {
        News newsOne = setupNews();
        News newsTwo = setupNews();
        assertEquals(2, newsDao.getAll().size());
        newsDao.deleteById(newsOne.getId());
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        News newsOne = setupNews();
        News newsTwo = setupNews();
        newsDao.clearAll();
        assertEquals(0, newsDao.getAll().size());
    }


    @Test
    public void NewsReturnsDepartmentCorrectly() throws Exception {
        Department testDepartment  = new Department("Marketing");
        Department altDepartment = new Department("Senior Management");
        departmentDao.add(testDepartment);
        departmentDao.add(altDepartment);

        News testNews = setupNews();

        newsDao.add(testNews);

        newsDao.addNewsToADepartment(testNews,testDepartment);
        newsDao.addNewsToADepartment(testNews,altDepartment);

        Department[] departments = {testDepartment,altDepartment}; //oh hi what is this? Observe how we use its assertion below.

        assertEquals(Arrays.asList(departments), newsDao.getAllDepartmentsForNews(testNews.getId()));
    }

    //helpers
    public News setupNews() {
        News news = new News("Morning Show","We are great to be here today",3);
        newsDao.add(news);
        return news;
    }
    public News setupAltNews() {
        News news = new News("Evening show","We are great to be out",1);
        newsDao.add(news);
        return news;
    }

}