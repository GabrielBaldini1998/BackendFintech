# BackendFintech — API REST Spring Boot

API REST de um sistema Fintech desenvolvido com Spring Boot, conectada ao banco de dados Oracle da FIAP.

---

## Tecnologias

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.4.5 |
| Spring Data JPA | 3.4.5 |
| Spring Validation | 3.4.5 |
| Oracle JDBC (ojdbc11) | gerenciado pelo Spring Boot |
| Maven | 3.x |

---

## Configuração do Banco de Dados

O projeto conecta obrigatoriamente à instância Oracle da FIAP:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
spring.datasource.username=rm567373
spring.datasource.password=090198
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Sequences necessárias no Oracle

Caso ainda não existam, execute no SQL Developer ou terminal Oracle:

```sql
CREATE SEQUENCE SEQ_USUARIO      START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_DESPESA      START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_RECEITA      START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_INVESTIMENTO START WITH 1 INCREMENT BY 1;
```

---

## Como executar

```bash
# Clonar o repositório
git clone https://github.com/GabrielBaldini1998/BackendFintech.git
cd BackendFintech

# Compilar e rodar
mvn clean package
mvn spring-boot:run
```

A API sobe em `http://localhost:8080`.

---

## Arquitetura e Fluxo

```
Cliente (Postman / Frontend)
        │
        ▼
  [ Controller ]          ← @RestController — recebe a requisição HTTP
        │
        ▼
  [ Service ]             ← @Service — aplica regras de negócio
        │
        ▼
  [ Repository ]          ← @Repository / JpaRepository — acessa o banco
        │
        ▼
  [ Oracle DB ]           ← oracle.fiap.com.br:1521:ORCL
```

### Fluxo detalhado por verbo HTTP

| Verbo | Fluxo |
|---|---|
| **GET** | Controller → Service.listarTodos() / buscarPorId() → Repository.findAll() / findById() → 200 OK |
| **POST** | Controller → Service.salvar() → validações de negócio → Repository.save() → 201 CREATED |
| **PUT** | Controller → Service.atualizar() → verifica existência → Repository.save() → 200 OK |
| **DELETE** | Controller → Service.deletar() → verifica existência → Repository.deleteById() → 204 NO CONTENT |
| **Não encontrado** | Service lança ResourceNotFoundException → GlobalExceptionHandler → 404 NOT FOUND |
| **Dados inválidos** | Bean Validation lança MethodArgumentNotValidException → GlobalExceptionHandler → 400 BAD REQUEST |

---

## Estrutura de Pacotes

```
br.com.fiap.jdbc
├── BancoApplication.java          ← ponto de entrada
├── config/
│   ├── WebConfig.java             ← configuração global de CORS
│   ├── DataSeeder.java            ← popula usuário admin + dados de exemplo no 1º start
│   └── SequenceInitializer.java   ← sincroniza as sequences Oracle com o maior ID existente
├── controller/
│   ├── UsuarioController.java
│   ├── TransacaoController.java
│   ├── CofrinhoController.java
│   └── AuthController.java        ← login (email + senha)
├── service/
│   ├── UsuarioService.java
│   ├── TransacaoService.java
│   └── CofrinhoService.java
├── repository/
│   ├── UsuarioRepository.java
│   ├── TransacaoRepository.java
│   └── CofrinhoRepository.java
├── model/
│   ├── Usuario.java
│   ├── Transacao.java
│   └── Cofrinho.java
├── dao/                           ← camada JDBC legada (mantida por compatibilidade)
│   └── UsuarioDAO.java
└── exception/
    ├── ResourceNotFoundException.java
    └── GlobalExceptionHandler.java
```

---

## Entidades e Tabelas

### Usuario → `T_FTC_USUARIO`

| Campo | Coluna | Tipo | Restrição |
|---|---|---|---|
| idUsuario | id_usuario | Long | PK, SEQ_USUARIO |
| nmCompleto | nm_completo | VARCHAR(100) | NOT NULL |
| dtNascimento | dt_nascimento | DATE | — |
| nmDocumento | nm_documento | VARCHAR(18) | NOT NULL, UNIQUE |
| tpTipo | tp_tipo | VARCHAR(5) | NOT NULL ("CPF" ou "CNPJ") |
| dsEmail | ds_email | VARCHAR(100) | NOT NULL |
| dsSenha | ds_senha | VARCHAR(100) | NOT NULL |

### Transacao → `T_FTC_TRANSACAO`

| Campo | Coluna | Tipo | Restrição |
|---|---|---|---|
| idTransacao | id_transacao | Long | PK, SEQ_TRANSACAO |
| tpTransacao | tp_transacao | VARCHAR(10) | NOT NULL ("RECEITA" ou "DESPESA") |
| dsTransacao | ds_transacao | VARCHAR(200) | NOT NULL |
| vlTransacao | vl_transacao | NUMBER | NOT NULL, > 0 |
| dtTransacao | dt_transacao | DATE | — |
| categoria | categoria | VARCHAR(50) | — |
| idUsuario | id_usuario | Long | FK → T_FTC_USUARIO |
| idCofrinho | id_cofrinho | Long | FK → T_FTC_COFRINHO (opcional) |

### Cofrinho → `T_FTC_COFRINHO`

| Campo | Coluna | Tipo | Restrição |
|---|---|---|---|
| idCofrinho | id_cofrinho | Long | PK, SEQ_COFRINHO |
| nmCofrinho | nm_cofrinho | VARCHAR(100) | NOT NULL |
| dsCofrinho | ds_cofrinho | VARCHAR(200) | — |
| vlMeta | vl_meta | NUMBER | padrão 0 |
| vlAtual | vl_atual | NUMBER | padrão 0 |
| dsIcone | ds_icone | VARCHAR(50) | — |
| dsCor | ds_cor | VARCHAR(20) | — |
| idUsuario | id_usuario | Long | FK → T_FTC_USUARIO |

### Relacionamentos

```
Usuario  ──< Transacao
         └──< Cofrinho ──< Transacao (via idCofrinho, opcional)
```

### Sequences necessárias no Oracle

```sql
CREATE SEQUENCE SEQ_USUARIO   START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_TRANSACAO START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE SEQ_COFRINHO  START WITH 1 INCREMENT BY 1;
```

---

## Endpoints da API

### Usuarios — `/api/usuarios`

| Método | URL | Descrição | Status |
|---|---|---|---|
| GET | `/api/usuarios` | Lista todos os usuários | 200 |
| GET | `/api/usuarios/{id}` | Busca usuário por ID | 200 / 404 |
| POST | `/api/usuarios` | Cria novo usuário | 201 |
| PUT | `/api/usuarios/{id}` | Atualiza usuário | 200 / 404 |
| DELETE | `/api/usuarios/{id}` | Remove usuário | 204 / 404 |

**Exemplo POST `/api/usuarios`:**
```json
{
  "nmCompleto": "Gabriel Baldini",
  "dtNascimento": "1998-01-09",
  "nmDocumento": "12345678901",
  "tpTipo": "CPF",
  "dsEmail": "gabriel@email.com",
  "dsSenha": "senha123"
}
```

---

### Auth — `/api/auth`

| Método | URL | Descrição | Status |
|---|---|---|---|
| POST | `/api/auth/login` | Autentica por email + senha | 200 / 401 |

**Exemplo POST `/api/auth/login`:**
```json
{ "email": "admin@fincheck.com", "senha": "123456" }
```

---

### Transações — `/api/transacoes`

| Método | URL | Descrição | Status |
|---|---|---|---|
| GET | `/api/transacoes` | Lista todas | 200 |
| GET | `/api/transacoes/{id}` | Busca por ID | 200 / 404 |
| GET | `/api/transacoes/usuario/{idUsuario}` | Lista por usuário | 200 |
| POST | `/api/transacoes` | Registra nova transação | 201 |
| PUT | `/api/transacoes/{id}` | Atualiza transação | 200 / 404 |
| DELETE | `/api/transacoes/{id}` | Remove transação | 204 / 404 |

**Exemplo POST `/api/transacoes`:**
```json
{
  "tpTransacao": "DESPESA",
  "dsTransacao": "Supermercado",
  "vlTransacao": 150.00,
  "dtTransacao": "2026-05-17",
  "categoria": "Alimentação",
  "idUsuario": 1
}
```

---

### Cofrinhos — `/api/cofrinhos`

| Método | URL | Descrição | Status |
|---|---|---|---|
| GET | `/api/cofrinhos` | Lista todos | 200 |
| GET | `/api/cofrinhos/{id}` | Busca por ID | 200 / 404 |
| GET | `/api/cofrinhos/usuario/{idUsuario}` | Lista por usuário | 200 |
| POST | `/api/cofrinhos` | Cria novo cofrinho | 201 |
| PUT | `/api/cofrinhos/{id}` | Atualiza cofrinho | 200 / 404 |
| DELETE | `/api/cofrinhos/{id}` | Remove cofrinho | 204 / 404 |

**Exemplo POST `/api/cofrinhos`:**
```json
{
  "nmCofrinho": "Viagem Europa",
  "dsCofrinho": "Fundo para viagem em 2027",
  "vlMeta": 15000.00,
  "vlAtual": 0,
  "dsIcone": "Plane",
  "dsCor": "#F59E0B",
  "idUsuario": 1
}
```

---

## Regras de Negócio

| Entidade | Regra |
|---|---|
| Usuario | Documento (CPF/CNPJ) único por cadastro |
| Usuario | E-mail único por cadastro |
| Transacao | tpTransacao deve ser "RECEITA" ou "DESPESA" |
| Transacao | Valor deve ser maior que zero |
| Cofrinho | vlMeta e vlAtual assumem 0 quando não informados |

---

## Tratamento de Erros

Todas as respostas de erro seguem o padrão:

```json
{
  "timestamp": "2025-05-17T10:30:00",
  "status": 404,
  "erro": "Usuário não encontrado com ID: 99"
}
```

| Situação | Status |
|---|---|
| Recurso não encontrado | 404 NOT FOUND |
| Dados inválidos (`@Valid`) | 400 BAD REQUEST |
| Erro interno inesperado | 500 INTERNAL SERVER ERROR |

---

## Integrantes do Grupo EQ1S

> Preencha com os nomes e RMs do grupo.

| Nome | RM |
|---|---|
|  | rm567373 |
