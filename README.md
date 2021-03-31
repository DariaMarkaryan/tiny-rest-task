# tiny-rest-task

Требования к программе:

Нужно написать серверную часть приложения (без UI части) по работе с пользователями и их телефонной книжкой.
Программа должна предоставлять REST API для:
* получения списка всех пользователей (владельцев телефонных книжек)
* создания, получения (по id), удаления, редактирования пользователя
* создания, получения (по id), удаления, редактирования записи в телефонной книжке
* получения списка всех записей в телефонной книжке пользователя
* поиск пользователей по имени (или его части)*, поиск телефонной записи по номеру телефона

---

## how to start

1. Clone the repo

2. Create Mysql database:

create database user_database

3. Change username and password:

open application.properties,

change spring.datasource.username and spring.datasource.password and url to yours database

4. Build and run the app with your favorite IDE or with maven:

The app will start running at http://localhost:8080.

---
The app defines following CRUD APIs:

GET: 
  /user/all
  /user/{id}
  /user/byname/{name}
  /user/{id}/phonebook
  /contact/{contactId}

POST: 
  /user
  /contact

PUT: 
  /user/{id}
  /contact/{contactId}

:DELETE 
/user/{id}
/contact/{contactId}
