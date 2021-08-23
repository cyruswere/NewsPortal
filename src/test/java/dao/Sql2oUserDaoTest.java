package dao;

import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;


public class Sql2oUserDaoTest {
    private static Connection conn; //these variables are now static.
    private static Sql2oNewsDao newsDao;//these variables are now static.
    private static Sql2oDepartmentDao departmentDao;
    private static Sql2oUserDao userDao;

    @BeforeClass //changed to @BeforeClass (run once before running any tests in this file)
    public static void setUp() throws Exception { //changed to static
        String connectionString = "jdbc:postgresql://localhost:5432/portal_test"; //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString,  "moringa", "kidero"); //changed user and pass to null for mac users...Linux & windows need strings
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
    public void addingUserSetsId() throws Exception {
        User testUser = setupUser();
        assertEquals(1, testUser.getId());
    }

    @Test
    public void getAll() throws Exception {
        User userOne = setupUser();
        User userTwo = setupUser();
        assertEquals(2, userDao.getAll().size());
    }



    @Test
    public void deleteById() throws Exception {
        User userOne = setupUser();
        User userTwo = setupUser();
        assertEquals(2, userDao.getAll().size());
        userDao.deleteById(userOne.getId());
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        User userOne = setupUser();
        User userTwo = setupUser();
        userDao.clearAll();
        assertEquals(0, userDao.getAll().size());
    }

    //helpers

    public User setupUser() {
        User user = new User("Odero", 4);
        userDao.add(user);
        return user;
    }

}