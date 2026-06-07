# 🚀 Task Manager Backend (Spring Boot)

A robust backend service for the **Task Manager application**, built using **Spring Boot, Spring Security, JWT Authentication, MySQL, and AI integration (Gemini API)**.
It provides secure REST APIs for task management, authentication, AI-powered features, and optional blockchain audit logging support.

---

# 📌 Features

## 🔐 Authentication & Security

* User Registration & Login
* JWT-based Authentication
* Role-based access control (if enabled)
* Spring Security integration
* Password encryption using BCrypt

---

## 📋 Task Management APIs

* Create Task
* Update Task
* Delete Task
* Fetch All Tasks
* Fetch Task by ID
* Mark Task as Completed
* User-specific task handling

---

## 🤖 AI Integration (Gemini API)

* Generate task descriptions using AI
* Improve existing task content
* Smart productivity assistance via REST endpoints

---

## ⛓️ Blockchain Support (Optional Module)

* Logs task actions on blockchain
* Smart contract integration support
* Works with Hardhat / Anvil local chain
* MetaMask-compatible audit logging

---

# 🛠️ Tech Stack

* Java 17+
* Spring Boot
* Spring Security
* JWT (JSON Web Token)
* Spring Data JPA
* MySQL
* Maven
* REST APIs
* Google Gemini API
* Solidity (for optional blockchain integration)

---

# 📂 Project Structure

```text
backend/
│
├── controller/
│   ├── AuthController.java
│   ├── TaskController.java
│   └── AIController.java
│
├── service/
│   ├── AuthService.java
│   ├── TaskService.java
│   └── AIService.java
│
├── repository/
│   ├── UserRepository.java
│   └── TaskRepository.java
│
├── model/
│   ├── User.java
│   └── Task.java
│
├── security/
│   ├── JwtFilter.java
│   ├── JwtUtil.java
│   └── SecurityConfig.java
│
└── config/
    └── AppConfig.java
```

---

# ⚙️ Installation & Setup

## 1️⃣ Clone Repository

```bash
git clone [https://github.com/your-username/taskmanager-backend.git](https://github.com/shreyasoniii/Taskmanager)
cd taskmanager-backend
```

---

## 2️⃣ Configure Database

Create a MySQL database:

```sql
CREATE DATABASE taskmanager;
```

---

## 3️⃣ Configure `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

server.port=8080

# JWT Secret
jwt.secret=your_jwt_secret_key

# Gemini API Key
gemini.api.key=your_gemini_api_key
```

---

## 4️⃣ Run Backend

```bash
mvn spring-boot:run
```

Backend will run at:

```text
http://localhost:8081
```

---

# 🔑 Authentication Flow

1. User registers via `/api/auth/register`
2. User logs in via `/api/auth/login`
3. Backend returns JWT token
4. Token is used in all protected requests:

```http
Authorization: Bearer <token>
```

---

# 📡 API Endpoints

## 🔐 Auth APIs

```http
POST /api/auth/register
POST /api/auth/login
```

---

## 📋 Task APIs

```http
GET    /api/tasks
POST   /api/tasks
PUT    /api/tasks/{id}
DELETE /api/tasks/{id}
GET    /api/tasks/{id}
```

---

## 🤖 AI APIs

```http
POST /api/ai/generate-description
POST /api/ai/improve-description
```

---

# 🔒 Security Features

* JWT Authentication
* Stateless session management
* Password encryption (BCrypt)
* Protected REST endpoints
* CORS configuration for frontend integration

---

# 🌐 CORS Configuration

Frontend (Vite) runs on:

```text
http://localhost:5173
```

Make sure CORS is enabled in backend:

```java
allowedOrigins = "http://localhost:5173"
```

---

# 🚀 Future Enhancements

* Refresh Token System
* Email Notifications
* Task Deadlines & Reminders
* Role-based Admin Panel
* AI Task Prioritization
* Cloud Deployment (AWS / Render)
* Production-grade logging system

---

# 👩‍💻 Author

**Shreya Soni**

Full Stack Java Developer

