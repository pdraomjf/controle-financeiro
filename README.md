# Controle Financeiro

## 📌 Visão Geral  
Esta é uma **API REST de Controle Financeiro**, desenvolvida para gerenciar usuários, contas e movimentações financeiras com autenticação JWT e documentação via Swagger.  
O objetivo é oferecer uma base sólida, escalável e bem estruturada, adequada tanto para estudo quanto para uso real.

---

## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Spring Security + JWT**
- **PostgreSQL**
- **Maven**
- **Swagger / OpenAPI 3**
- **Docker**
- **Deploy na Koyeb**

---

## 📁 Estrutura do Projeto (src/main/java)
```
com.augustodev.api_controle_financeiro
│
├── config
│   └── SwaggerConfig
│
├── controller
│   ├── AuthController
│   ├── ContaController
│   ├── MovimentacaoController
│   └── UsuarioController
│
├── dto
│
├── exception
│   ├── ContaNaoEncontradaException
│   ├── GlobalExceptionHandler
│   ├── MovimentacaoNaoEncontradaException
│   ├── SaldoInsuficienteException
│   └── UsuarioNaoEncontradoException
│
├── models
│   ├── Conta
│   ├── Movimentacao
│   ├── TipoMovimentacao
│   ├── TipoUsuario
│   └── Usuario
│
├── repository
│
├── security
│   ├── AuthenticatedUser
│   ├── CorsConfig
│   ├── JwtFilter
│   ├── JwtUtil
│   └── SecurityConfig
│
├── service
│   ├── AuthService
│   ├── ContaService
│   ├── MovimentacaoService
│   └── UsuarioService
│
└── ApiControleFinanceiroApplication
```

---

## 🔐 Autenticação
A API utiliza **JWT**.  
1. Faça login em `/login`  
2. Receba o token  
3. Utilize no header → `Authorization: Bearer <token>`

O Swagger permite autenticação automática via botão **Authorize** (quando ativado pelo deploy HTTPS).

---

## 📚 Documentação
A documentação em Swagger está disponível em:

👉 **`/v3/api-docs`**  
👉 **`/swagger-ui.html`**

Durante o deploy na Koyeb:  
🔗 [https://marvellous-luce-augustodev-f348a5bf.koyeb.app/swagger-ui/index.html](https://marvellous-luce-augustodev-f348a5bf.koyeb.app/swagger-ui/index.html)

---

## 🐋 Docker
### **Build**
```bash
docker build -t controle-financeiro .
```

### **Run**
```bash
docker run -p 8080:8080 --env-file .env controle-financeiro
```

---

## 🌐 Deploy
Hospedado utilizando **Koyeb** com:
- Build automatizado via Dockerfile
- Instância com PostgreSQL externo

---

## 🧪 Endpoints Principais
### 👤 Usuários
- `GET /usuarios/`
- `POST /usuarios/`
- `PUT /usuarios/`
- `GET /usuarios/{id}`
- `DELETE /usuarios/{id}`

### 💰 Conta
- `GET /conta/`
- `POST /conta/`
- `GET /conta/{id}`
- `GET /conta/saldo`

### 🔄 Movimentações
- `GET /movimentacao/`
- `POST /movimentacao/`
- `PUT /movimentacao/`

### 🔐 Autenticação
- `POST /login/`

---

## 🖥️ Como Rodar Localmente
1. Configure seu PostgreSQL  
2. Copie o arquivo `.env.example` para um novo arquivo chamado .env:

```env
DB_URL=jdbc:postgresql://localhost:5432/financeiro
DB_USER=postgres
DB_PASSWORD=senha
JWT_SECRET=chave_secreta
EXPIRATION_TIME=preencha_em_milissegundos
SPRING_PROFILES_ACTIVE=dev
```

3. Execute:
```bash
mvn spring-boot:run
```

---

## 📸 Capturas

<img width="1919" height="951" alt="image" src="https://github.com/user-attachments/assets/55c20e1e-1713-4a39-96d5-199281302f37" />

<img width="1919" height="950" alt="image" src="https://github.com/user-attachments/assets/b417fc5b-8ef9-4a8c-a1e3-98f403b30d1e" />

<img width="1919" height="1037" alt="image" src="https://github.com/user-attachments/assets/573b21d0-df88-4dbb-83cd-5868af4955ce" />


---

## 💻 Autor
**Pedro Augusto**  

---

## ⭐ Contribuições
Sugestões, issues e pull requests são sempre bem-vindos!

