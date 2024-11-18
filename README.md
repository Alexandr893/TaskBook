# TaskBook
Приложение "Задачник"


## Особенности

- CRUD операции для задач
- Аутентификация и авторизация с использованием JWT
- Управление ролями пользователей
- API документация через Swagger
- Поддержка работы с базой данных PostgreSQL

## Технологии

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA (Hibernate)
- PostgreSQL
- Docker
- Maven

## Установка и запуск

### Предварительные требования

- [JDK 17+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)
- [PostgreSQL](https://www.postgresql.org/)

### Шаги для запуска

1. **Клонирование репозитория**

   ```bash
   git clone https://github.com/yourusername/task-manager.git
   cd task-manager

2. Убедитесь, что у вас установлен PostgreSQL и доступны настройки подключения

   spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password


3. Используйте Maven для сборки и запуска приложения

   mvn clean install
   mvn spring-boot:run

### Docker

**Чтобы контейнеризировать и запустить приложение с Docker**

1.Сборка Docker image Выполните команду для создания изображения Docker:

docker build -t task-manager:latest .


2.Запуск Docker контейнера Запустите контейнер Docker со следующим:

docker run -d -p 9090:9090 task-manager:latest

   
   
