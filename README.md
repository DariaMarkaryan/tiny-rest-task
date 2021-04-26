# tiny-rest-task

## How to start

1. Clone the repo

2. Create Mysql database:

create database user_database

3. Change username and password:

open application.properties,

change spring.datasource.username and spring.datasource.password and url to yours database

4. Build and run the app with your favorite IDE or with maven. The app will start running at http://localhost:8080.

---
## The app defines following CRUD APIs:

```
GET: 
  /users
  /users/{id}
  /users/searchbyname/{name}
  /users/{id}/phonebook
  /contacts/{contactId}

POST: 
  /users
  /contacts

PUT: 
  /users/{id}
  /contacts/{contactId}

:DELETE 
/users/{id}
/contacts/{contactId}
```
