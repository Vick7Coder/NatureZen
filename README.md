### NatureZen

**Tech Stack:**

<img src="https://angular.io/assets/images/logos/angular/angular.svg" width="24"> Angular

<img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" width="24"> Spring Boot

<img src="https://www.vectorlogo.zone/logos/mysql/mysql-official.svg" width="24"> MySQL

**Exploit DB with Docker Container**
~~~
docker run --detach --env MYSQL_ROOT_PASSWORD='your_root_password' --env MYSQL_USER=natureAdmin --env MYSQL_PASSWORD=abc123 --env MYSQL_DATABASE=nature_zen --name mysql --publish 3306:3306 mysql:8-oracle
~~~
**REST API**
[Document here](http://localhost:8080/swagger-ui/index.html#/ "Swagger-ui").
