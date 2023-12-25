# TeacherPanel-Spring-REST-Controller

The project is a REST Controller for effective management of teacher data along with the organization of groups and teacher activities. Performed also unit tests of each method.

Technology used in the project: Java using Spring Boot, MySQL.

The operation of the application was tested using the Postman:
- POST: /api/teacher - adds a teacher
- DELETE: /api/teacher/:id - deletes the teacher
- GET: /api/teacher/csv - returns all teachers in the form of a CSV file
- GET: /api/group - returns all teacher groups
- POST: /api/group - adds a new teaching group
- DELETE: /api/group/:id - deletes the teaching group
- GET: /api/group/:id/teacher - returns all teachers from a given group
- GET: /api/group/:id/fill - returns the percentage fill of a given group
- POST: /api/rating - adds a rating for the teaching group
