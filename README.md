# NatureZen

**Exploit DB with Docker Container**
~~~
docker run --detach --env MYSQL_ROOT_PASSWORD='your_root_password' --env MYSQL_USER=natureAdmin --env MYSQL_PASSWORD=abc123 --env MYSQL_DATABASE=nature_zen --name mysql --publish 3306:3306 mysql:8-oracle
~~~
