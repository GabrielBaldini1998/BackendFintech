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
│   └── WebConfig.java             ← configuração global de CORS
├── controller/
│   ├── UsuarioController.java
│   ├── ContaController.java
│   ├── DespesaController.java
│   ├── ReceitaController.java
│   └── InvestimentoController.java
├── service/
│   ├── UsuarioService.java
│   ├── ContaService.java
│   ├── DespesaService.java
│   ├── ReceitaService.java
│   └── InvestimentoService.java
├── repository/
│   ├── UsuarioRepository.java
│   ├── ContaRepository.java
│   ├── DespesaRepository.java
│   ├── ReceitaRepository.java
│   └── InvestimentoRepository.java
├── model/
│   ├── Usuario.java
│   ├── Conta.java
│   ├── Despesa.java
│   ├── Receita.java
│   └── Investimento.java
├── dao/                           ← camada JDBC legada (mantida por compatibilidade)
│   ├── UsuarioDAO.java
│   ├── ContaDAO.java
│   ├── DespesaDAO.java
│   ├── ReceitaDAO.java
│   └── InvestimentoDAO.java
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
| nmCpfUsuario | nm_cpf_usuario | VARCHAR(11) | NOT NULL, UNIQUE |
| dsEmail | ds_email | VARCHAR(100) | NOT NULL |
| dsSenha | ds_senha | VARCHAR(100) | NOT NULL |

### Conta → `T_FTC_CONTA`

| Campo | Coluna | Tipo | Restrição |
|---|---|---|---|
| numeroDaConta | numero_da_conta | VARCHAR(20) | PK (string) |
| titular | titular | VARCHAR(100) | NOT NULL |
| agencia | agencia | VARCHAR(10) | NOT NULL |
| tipo | tipo | VARCHAR(20) | NOT NULL |
| saldo | saldo | NUMBER | — |
| idUsuario | id_usuario | Long | FK → T_FTC_USUARIO |

### Despesa → `T_FTC_DESPESA`

| Campo | Coluna | Tipo | Restrição |
|---|---|---|---|
| idDespesa | id_despesa | Long | PK, SEQ_DESPESA |
| tpDespesa | tp_despesa | VARCHAR(50) | NOT NULL |
| vlDespesa | vl_despesa | NUMBER | NOT NULL, > 0 |
| dtDespesa | dt_despesa | DATE | — |
| numeroDaConta | numero_da_conta | VARCHAR(20) | FK → T_FTC_CONTA |

### Receita → `T_FTC_RECEITA`

| Campo | Coluna | Tipo | Restrição |
|---|---|---|---|
| idReceita | id_receita | Long | PK, SEQ_RECEITA |
| dtReceita | dt_receita | DATE | — |
| vlRecebido | vl_recebido | NUMBER | NOT NULL, > 0 |
| dsReceita | ds_receita | VARCHAR(200) | NOT NULL |
| numeroDaConta | numero_da_conta | VARCHAR(20) | FK → T_FTC_CONTA |

### Investimento → `T_FTC_INVESTIMENTO`

| Campo | Coluna | Tipo | Restrição |
|---|---|---|---|
| idInvestimento | id_investimento | Long | PK, SEQ_INVESTIMENTO |
| nmAplicacao | nm_aplicacao | VARCHAR(100) | NOT NULL |
| nmBancoCorretora | nm_banco_corretora | VARCHAR(100) | NOT NULL |
| vlAplicacao | vl_aplicacao | NUMBER | NOT NULL, > 0 |
| dtAplicacao | dt_aplicacao | DATE | — |
| dtVencimentoAplicacao | dt_vencimento_aplicacao | DATE | deve ser > dtAplicacao |
| numeroDaConta | numero_da_conta | VARCHAR(20) | FK → T_FTC_CONTA |

### Relacionamentos

```
Usuario  ──< Conta ──< Despesa
                  └──< Receita
                  └──< Investimento
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
  "nmCpfUsuario": "12345678901",
  "dsEmail": "gabriel@email.com",
  "dsSenha": "senha123"
}
```

---

### Contas — `/api/contas`

| Método | URL | Descrição | Status |
|---|---|---|---|
| GET | `/api/contas` | Lista todas as contas | 200 |
| GET | `/api/contas/{numeroDaConta}` | Busca conta por número | 200 / 404 |
| POST | `/api/contas` | Cria nova conta | 201 |
| PUT | `/api/contas/{numeroDaConta}` | Atualiza conta | 200 / 404 |
| DELETE | `/api/contas/{numeroDaConta}` | Remove conta | 204 / 404 |

**Exemplo POST `/api/contas`:**
```json
{
  "numeroDaConta": "0001-2",
  "titular": "Gabriel Baldini",
  "agencia": "0001",
  "tipo": "corrente",
  "saldo": 1500.00,
  "idUsuario": 1
}
```

---

### Despesas — `/api/despesas`

| Método | URL | Descrição | Status |
|---|---|---|---|
| GET | `/api/despesas` | Lista todas as despesas | 200 |
| GET | `/api/despesas/{id}` | Busca despesa por ID | 200 / 404 |
| POST | `/api/despesas` | Registra nova despesa | 201 |
| PUT | `/api/despesas/{id}` | Atualiza despesa | 200 / 404 |
| DELETE | `/api/despesas/{id}` | Remove despesa | 204 / 404 |

**Exemplo POST `/api/despesas`:**
```json
{
  "tpDespesa": "Alimentação",
  "vlDespesa": 150.00,
  "dtDespesa": "2025-05-17",
  "numeroDaConta": "0001-2"
}
```

---

### Receitas — `/api/receitas`

| Método | URL | Descrição | Status |
|---|---|---|---|
| GET | `/api/receitas` | Lista todas as receitas | 200 |
| GET | `/api/receitas/{id}` | Busca receita por ID | 200 / 404 |
| POST | `/api/receitas` | Registra nova receita | 201 |
| PUT | `/api/receitas/{id}` | Atualiza receita | 200 / 404 |
| DELETE | `/api/receitas/{id}` | Remove receita | 204 / 404 |

**Exemplo POST `/api/receitas`:**
```json
{
  "dtReceita": "2025-05-17",
  "vlRecebido": 5000.00,
  "dsReceita": "Salário maio",
  "numeroDaConta": "0001-2"
}
```

---

### Investimentos — `/api/investimentos`

| Método | URL | Descrição | Status |
|---|---|---|---|
| GET | `/api/investimentos` | Lista todos os investimentos | 200 |
| GET | `/api/investimentos/{id}` | Busca investimento por ID | 200 / 404 |
| POST | `/api/investimentos` | Registra novo investimento | 201 |
| PUT | `/api/investimentos/{id}` | Atualiza investimento | 200 / 404 |
| DELETE | `/api/investimentos/{id}` | Remove investimento | 204 / 404 |

**Exemplo POST `/api/investimentos`:**
```json
{
  "nmAplicacao": "Tesouro Selic",
  "nmBancoCorretora": "XP Investimentos",
  "vlAplicacao": 2000.00,
  "dtAplicacao": "2025-05-17",
  "dtVencimentoAplicacao": "2026-05-17",
  "numeroDaConta": "0001-2"
}
```

---

## Regras de Negócio

| Entidade | Regra |
|---|---|
| Usuario | CPF único por cadastro |
| Usuario | E-mail único por cadastro |
| Conta | Saldo inicial não pode ser negativo |
| Despesa | Valor deve ser positivo |
| Despesa | Conta informada deve existir |
| Receita | Valor recebido deve ser positivo |
| Receita | Conta informada deve existir |
| Investimento | Valor de aplicação deve ser positivo |
| Investimento | Data de vencimento deve ser posterior à data de aplicação |
| Investimento | Conta informada deve existir |

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
