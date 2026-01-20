# Order Notification Service (POC)

## Objetivo

Esta POC tem como objetivo demonstrar, de forma simples e prática, a construção de um **microserviço backend em Java** com foco em:

* Desenvolvimento de APIs REST
* Persistência de dados
* Comunicação assíncrona via mensageria
* Containerização
* Noções de infraestrutura como código

A ideia não é complexidade, mas sim **clareza de fluxo, boas práticas e entendimento arquitetural nos meus estudos pessoais.**.

---

## Visão Geral da Solução

O serviço é responsável por criar pedidos e, após persistí-los no banco de dados, **publicar um evento assíncrono** informando que um pedido foi criado.

Fluxo principal:

```
O cliente envia uma requisição REST para criar um pedido.
                       ↓
O PedidoController recebe os dados e aciona o PedidoService.
                       ↓
O serviço persiste o pedido no banco de dados PostgreSQL via Spring Data JPA.
                       ↓
Após a persistência, o serviço publica uma mensagem no AWS SQS.
                       ↓
O PedidoListener (Consumer) detecta a nova mensagem na fila e a processa de forma assíncrona.
```

---

## Stack Utilizada

* **Java 17**
* **Spring Boot**
* **Spring Web (REST)**
* **Spring Data JPA**
* **PostgreSQL**
* **Docker / Docker Compose**
* **Swagger / OpenAPI**
* **JUnit + Mockito**
* **AWS SQS** (LocalStack para ambiente local)
* **Terraform** (estrutura básica)

---

## Estrutura do Projeto

```
order-notification-service
├── docker/                # Arquivos de configuração Docker
├── infra/                 # Infraestrutura como código (Terraform)
├── src/main/java/com/example/ordernotification/
│   ├── config/            # Configurações de Beans (Jackson, SQS)
│   ├── controller/        # Endpoints REST (PedidoController)
│   ├── domain/            # Entidades de domínio (Pedido)
│   ├── dto/               # Objetos de transferência (PedidoRequestDTO)
│   ├── repository/        # Interfaces de persistência (PedidoRepository)
│   ├── service/           # Lógica de negócio e Listeners (PedidoService, PedidoListener)
│   └── OrderNotificationServiceApplication.java
├── src/main/resources/    # Configurações (application.yml)
├── docker-compose.yml     # Orquestração de serviços locais
├── pom.xml                # Gestão de dependências Maven
└── start.sh               # Script de automação para o ambiente
```

---
## Execução Automática
Para limpar o ambiente, subir os containers e aplicar as configurações de infraestrutura, execute:
```bash
chmod +x start.sh
./start.sh
```

## Endpoints

### Criar Pedido

`POST /orders`

Exemplo de payload:

```json
{
  "nome": "Netuno",
  "produto": "Café especial",
  "preco": 15.90
}
```


## Comunicação Assíncrona

AApós a criação e persistência de um pedido, o serviço publica uma mensagem no SQS contendo o estado completo da entidade. Isso permite que sistemas downstream processem a notificação sem dependência imediata de consultas ao banco de dados principal.
Formato da Mensagem (JSON):
```json
{
  "id": "6ba7b810-9dad-11d1-80b4-00c04fd430c8",
  "nome": "Netuno",
  "produto": "Cafe Especial",
  "valor": 15.90,
  "criadoEm": "2026-01-20T00:10:00"
}
```

Essa abordagem tende a simular um cenário real de **arquitetura orientada a eventos**, permitindo desacoplamento entre serviços.

---

## Segurança (Considerações)

Nesta POC, a autenticação não é implementada diretamente.

Em um cenário produtivo, este serviço seria protegido por um **provedor de identidade**, como **Keycloak**, garantindo autenticação e autorização via token.

---

## Testes

A POC possui **testes unitários** focados na camada de serviço, validando:

* Criação do pedido
* Persistência dos dados
* Publicação do evento (mock do SQS)

---

## Execução com Docker

```bash
docker-compose up
```

O ambiente sobe:

* Aplicação Spring Boot
* Banco PostgreSQL
* LocalStack (SQS)

---

## Considerações Finais

Essa POC foi construída com foco em reforço de estudos pessoais com **clareza, boas práticas e entendimento de fluxo**, representando um cenário realista de um contexto de microsserviços.
