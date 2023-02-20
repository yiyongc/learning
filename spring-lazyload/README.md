# Spring-lazyload Practice Project

This project is used to understand how Hibernate SQL queries are being called when using EAGER fetching or LAZY fetching for ManyToOne relationships. Standard interface implementations are omitted in order to quickly spin up a project for learning / debugging purposes.

Models
- Student
- Course

The project is a bare bones project with a few endpoints
1. POST /loadDefault - Loads a few default entities into the database.
2. GET /students/{id}/courses - Fetches the course which the student is enrolled in.
3. GET/courses/{id}/students - Fetches the students who have enrolled in the course.
