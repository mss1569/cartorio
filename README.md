[![Build](https://github.com/mss1569/cartorio/workflows/CI/badge.svg)](https://github.com/mss1569/cartorio/actions)
[![codecov](https://codecov.io/gh/mss1569/cartorio/branch/main/graph/badge.svg?token=DZpoBFf5VV)](https://codecov.io/gh/mss1569/cartorio)

# API RESTful para controle de cartorios.

### [Heroku - Swagger UI](https://mss1569-cartorio.herokuapp.com/swagger-ui.html)

### Como rodar:

#### Usando Docker:
Opção 1: usando H2DB:
>```docker run --name cartorio -p 8080:8080 mss1569/cartorio```

Opção 2: usando mysql:
>```
>docker run --name cartorio -p 8080:8080 \
>-e SPRING_PROFILES_ACTIVE=prod \
>-e JDBC_DATABASE_URL="jdbc:mysql://HOST:3306/DATABASE?createDatabaseIfNotExist=true" \
>-e JDBC_DATABASE_USERNAME="user" \
>-e JDBC_DATABASE_PASSWORD="password" mss1569/cartorio
>```

#### Usando Maven:
Por padrão a aplicação ira utilizar o H2DB para utilizar o MySql basta adicionar as variaveis de ambiente exemplificadas na execução com docker.
>```
>mvn -B package --file pom.xml
>java -jar target/cartorio-0.0.1-SNAPSHOT.jar
>```

#### [Localhost - Swagger UI](http://localhost:8080/swagger-ui.html)

### Objetivo:

Desenvolver um sistema que permite cadastrar, exibir, listar, editar e excluir cartórios alem de suas certidões. Um cartório possui nome e endereço,
e pode emitir uma ou varias certidões, cada certidão possui um nome.

![uml](https://user-images.githubusercontent.com/33636621/104828649-5ada1d00-584a-11eb-9e5c-08ce451ea332.png)

Devido ao fato de que uma certidão só pode ser emitida a partir de um cartório, a rota para criar uma certidão foi definida como recurso da rota de cartórios:
>```POST /notaries/{notaryId}/certificates```

Juntamente com a rota de busca de certidões emitidas por cartório:
>```GET /notaries/{notaryId}/certificates```

### Etapas:

- [x] Start Spring
- [x] Domain
  - [x] Notary
  - [x] Certificate
- [x] Repository
  - [x] Notary
  - [x] Certificate
- [x] Service
  - [x] Notary
  - [x] Certificate
- [x] Controller
  - [x] Notary
  - [x] Certificate
- [x] Swagger
- [x] Heroku
- [x] Docker

### Tecnologias:

- [x] Java 11
- [x] Maven
- [x] Spring Boot 2.4
- [x] Spring WEB
- [x] Spring Data JPA
- [x] JUnit5
- [x] Docker
- [x] Heroku
- [x] Github Actions
- [x] Codecov
- [x] H2DB
- [x] MySql
- [x] Lombok
- [x] Jacoco
- [x] ModelMapper
- [x] SpringDoc
