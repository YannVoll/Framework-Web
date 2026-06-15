# EduLab — Plataforma de Cursos Online

Aplicação web completa de cursos online construída com **Spring Boot 3**, **Spring Security**, **Thymeleaf** e **PostgreSQL**.

---

## 🚀 Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.1.7 |
| Spring Security | 6 |
| Spring Data JPA | 3.1.7 |
| Thymeleaf | 3.1 |
| PostgreSQL | 16 |
| Docker | — |
| Maven | 3.9+ |

---

## 📦 Rodando localmente

### Pré-requisitos
- Java 17+
- Maven 3.9+
- PostgreSQL rodando em `localhost:5432`

### Banco de dados
```sql
CREATE DATABASE coursedb;
```

### Subir a aplicação
```bash
mvn clean package -DskipTests
java -jar target/course-platform-0.0.1-SNAPSHOT.jar
```

Acesse: http://localhost:8080

---

## 🐳 Rodando com Docker

```bash
docker compose up -d
```

Acesse: http://localhost:4001

---

## 👤 Usuários de teste

| Perfil | E-mail | Senha |
|---|---|---|
| Admin | admin@edulab.com | admin123 |
| Aluno | vollbyan@gmail.com | senha123 |
| Aluno | bruno.lima@example.com | segredo456 |

---

## 🗺️ Rotas principais

### Web
| Rota | Descrição | Acesso |
|---|---|---|
| `GET /` | Página inicial | Público |
| `GET /courses` | Lista de cursos | Público |
| `GET /courses/{id}` | Detalhe do curso | Público |
| `GET /login` | Tela de login | Público |
| `GET /register` | Cadastro de usuário | Público |
| `POST /enroll/{courseId}` | Matricular-se | Autenticado |
| `GET /dashboard` | Área do aluno | Autenticado |
| `GET /admin` | Painel admin | ADMIN |
| `GET /admin/courses/new` | Criar curso | ADMIN |
| `GET /admin/courses/{id}/edit` | Editar curso | ADMIN |
| `GET /admin/courses/{id}/lessons` | Gerenciar aulas | ADMIN |
| `GET /admin/courses/{id}/enrollments` | Ver alunos | ADMIN |

### API REST
| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/courses` | Listar cursos |
| GET | `/api/courses/{id}` | Buscar curso |
| POST | `/api/courses` | Criar curso |
| PUT | `/api/courses/{id}` | Atualizar curso |
| DELETE | `/api/courses/{id}` | Remover curso |
| POST | `/api/courses/{id}/lessons` | Adicionar aula |
| GET | `/api/users` | Listar usuários |
| GET | `/api/users/{id}` | Buscar usuário |
| POST | `/api/users` | Criar usuário |
| PUT | `/api/users/{id}` | Editar usuário |
| DELETE | `/api/users/{id}` | Remover usuário |
| GET | `/api/enrollments` | Listar matrículas |
| GET | `/api/enrollments/user/{id}` | Matrículas por usuário |
| DELETE | `/api/enrollments/{id}` | Cancelar matrícula |

> **Atenção:** todos os endpoints `/api/**` exigem autenticação com perfil ADMIN.

---

## 🔐 Segurança

- Senhas criptografadas com **BCrypt**
- Autenticação via **Spring Security**
- Rotas de admin protegidas por `ROLE_ADMIN`
- Validação de matrícula duplicada
- CSRF ativo nas rotas web (desabilitado apenas para API)
