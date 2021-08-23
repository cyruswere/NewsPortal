CREATE DATABASE portal;
\c portal;

CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR,
                                     deptId int
);

CREATE TABLE IF NOT EXISTS departments (
                                           id SERIAL PRIMARY KEY,
                                           name VARCHAR
);

CREATE TABLE IF NOT EXISTS news (
                                    id SERIAL PRIMARY KEY,
                                    title VARCHAR,
                                    content VARCHAR,
                                    authorid INTEGER
);

CREATE TABLE IF NOT EXISTS news_depatments (
                                               id SERIAL PRIMARY KEY,
                                               newsid INTEGER,
                                               departmentid INTEGER
);


CREATE DATABASE portal_test WITH TEMPLATE portal;