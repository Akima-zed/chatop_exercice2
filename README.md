# ChâTop – Backend Spring Boot

**Projet d’exercice OpenClassrooms – Gestion des locations et utilisateurs avec JWT et Swagger**

---

## 1️⃣ Description

ChâTop est une API RESTful développée avec **Spring Boot 3**, sécurisée avec **Spring Security + JWT**, et documentée avec **Swagger (Springdoc 2.0.3)**.

- Authentification via JWT (`/auth/register`, `/auth/login`)
- Gestion des utilisateurs, locations, images, messages
- Backend structuré **Controller / Service / Repository**
- Documentation interactive Swagger pour tester toutes les routes avec Springdoc 2.0.3 pour Swagger.

---

## 2️⃣ Prérequis

- Java 17+
- Maven 3.8+
- MySQL 8+
- Mockoon
- Insomnia / Swagger UI pour tests

---
## Installation et lancement

1. **Créer le projet** (tu as déjà utilisé Spring Initializr).
2. **Configurer la base de données MySQL** :

```sql
CREATE DATABASE chatop_db;
```

3. **Configurer application.properties** :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/chatop_db
spring.datasource.username=<DB_USER>
spring.datasource.password=<DB_PASSWORD>

jwt.secret=<TON_SECRET>
```
4. **Compiler et lancer l’application** :
```bash
mvn clean install
mvn spring-boot:run
```

## Swagger UI
- Accessible sans authentification :
```text
http://localhost:3001/swagger-ui/index.html
```
- Routes principales :
    POST /auth/register – créer un utilisateur
    POST /auth/login – obtenir le JWT
    Toutes les autres routes nécessitent le token JWT (clique sur Authorize et colle Bearer <token>)

- Exemple request / response Swagger :
- POST /auth/register:
```json
{
  "name": "bob",
  "email": "bob@example.com",
  "password": "motdepasse"
}
```
- Response :
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## Structure du projet
```text
src/main/java/com.chatop
 ├─ chatop
 │   ├─ Auth        # Controllers REST (login/register)
 │   │   └─ dto     # DTOs pour Auth : AuthResponse, LoginRequest, RegisterRequest
 │   ├─ config      # Swagger, JWT, Spring Security
 │   ├─ controller  # Controllers REST supplémentaires
 │   ├─ dto         # DTOs
 │   ├─ entity      # Entités JPA
 │   ├─ mapper      # Mappers Dto <-> Entity → DTO <-> Entity
 │   └─ repository  # JPA Repositories

```
## Branche principale

Tu es sur la branche master.
Pour ajouter le README :
```
git add README.md
git commit -m "Add README with Swagger and JWT instructions"
git push origin master
````

## Tests
- Swagger UI permet de tester toutes les routes.
- Insonmnia peut être utilisé avec JWT :
```
Authorization: Bearer <token>
```

## Remarques sécurité
- Passwords hashés avec BCrypt
- JWT utilisé pour sécuriser toutes les routes (sauf login/register/Swagger)
- Credentials DB externes au code
