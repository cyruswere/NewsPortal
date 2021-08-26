CREATE DATABASE news_portal;
\c news_portal;


CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    name varchar,
    title varchar,
    roles varchar,
    associated_department varchar
);


CREATE TABLE IF NOT EXISTS news(
    id SERIAL PRIMARY KEY,
    header varchar,
    content varchar,
    written_by varchar
);

CREATE TABLE IF NOT EXISTS departments(
    id SERIAL PRIMARY KEY,
    name varchar,
    description varchar,
    number_of_employees integer
);

CREATE DATABASE news_portal_test WITH TEMPLATE news_portal;