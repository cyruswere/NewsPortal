# NewsPortal

A Java Spark back end news Portal API.

## Getting Started

ensure to follow these instructions to set upa and run succefully. 

## Setup Instructions

* Ensure gradle is installed, synched and all dependencies donwloaded. 
* Run this command to clone into your local repo
```
git clone https://github.com/cyruswere/NewsPortal.git

```

## Database set up Instructions



run
 
 ```
 $ psql < create.sql
 ```

if this doesn't work, go and do this manually. as below

```
CREATE DATABASE portal;

```

```
\c portal;

```

```
CREATE TABLE IF NOT EXISTS users (
id SERIAL PRIMARY KEY,
name VARCHAR,
deptId int
);
```

```
CREATE TABLE IF NOT EXISTS departments (
id SERIAL PRIMARY KEY,
name VARCHAR
);
```

```
CREATE TABLE IF NOT EXISTS news (
id SERIAL PRIMARY KEY,
title VARCHAR,
content VARCHAR,
authorid INTEGER
);
```

```
CREATE TABLE IF NOT EXISTS news_depatments (
id SERIAL PRIMARY KEY,
newsid INTEGER,
departmentid INTEGER
);
```

```
CREATE DATABASE portal_test WITH TEMPLATE portal;
```



* Navigate to the root folder of the repo via your terminal. 
* Run this command to start gradle via the terminal 

```
$ gradle run

```

* Use this URL on your broswer `localhost:4567` , you will see 
`{ 
     "Welcome to the news portal"

     }`




 | URL end point | Description |
        |:---        |          ---: |

        |Departments|

        | ``/departmets/new``       | POST | Url to create a new department  |
        | ``/departmets``           | GET | Url to view a list of all departments  |
        | ``/departmets/id``        | GET | Url view a single department based on id |
        | ``/departmets/id/news``   | GET   | Url to view news items of a specifc department - ``ie General``  |
          
        |Users|

        | ``/users/new``   | POST | Url to create a new user  |
        | ``/users``       | GET  |Url to view a list of all users  |
        | ``/users/id``    | GET  |Url view a single user based on id |
          

        |News|

        | ``/news/new``                        | POST | Url to create a new department  |
        | ``/news``                            | GET  | Url to view a list of all news brifing  |
        | ``/news/id``                         | GET  | Url view a single news briefing based on id |
        | ``/news/id/departmets/deptid``       | POST | Url to add a news briefing to a specific department |
        | ``/news/id/departmets``              | GET  | Url to view all the departments that are associated with a news briefing item. 


* when testing with postman, Use  
  
```
https://news-portal3.herokuapp.com/

```

, as the base URL.  




## Technologies used
- Java
- Gradle
- J-Unit5
- GSON



## Bugs being worked on
- It is open for critique and improvements

## Support and contact details
To help grow and make this product better, reach out to [email](mailto:cyruswere01@gmail.com).
### LICENSE
[![License: ISC](https://img.shields.io/badge/License-ISC-yellow.svg)](/LICENSE)

Copyright &copy; 2021 **[Cyrus Were](https://github.com/cyruswere)**
