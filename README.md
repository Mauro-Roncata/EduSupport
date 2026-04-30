# EduSupport API - SMED

Uma API RESTful desenvolvida em Java e Spring Boot para o gerenciamento de chamados de suporte técnico, projetada especificamente para atender a demanda de infraestrutura de TI em uma rede de ensino municipal.

## 📖 Sobre o Projeto

O EduSupport atua como o motor de uma central de serviços de TI (Helpdesk), conectando as necessidades técnicas de diversas escolas com a equipe de suporte central. A arquitetura foi desenhada para eliminar o atrito na abertura de chamados e garantir a integridade dos dados, substituindo formulários complexos por um fluxo autenticado e automatizado.

Utilizando o padrão OAuth2 integrado ao Google Workspace, o sistema garante que nenhum dado sensível ou senha precise ser armazenado localmente. Além disso, a aplicação possui uma inteligência de roteamento na camada de serviço que intercepta o e-mail institucional do usuário logado (ex: diretoria.escola@...) e mapeia automaticamente a origem do chamado no banco de dados. Isso impede inconsistências na base de dados e agiliza a triagem para os técnicos, que recebem uma fila de atendimentos padronizada, paginada e com controle de prioridades.

## 🚀 Tecnologias e Arquitetura

O backend foi construído com foco em escalabilidade e manutenção, utilizando os padrões de mercado para o ecossistema Spring:

* **Linguagem:** Java
* **Framework Core:** Spring Boot 3
* **Segurança:** Spring Security com integração OAuth2 Client (Google Login)
* **Persistência e ORM:** Spring Data JPA / Hibernate
* **Banco de Dados:** MySQL
* **Padrões de Projeto:** REST, Layered Architecture (Controller, Service, Repository), Data Transfer Objects (DTO) Pattern para isolamento de dados da API.

## ⚙️ Principais Funcionalidades

* **Autenticação Segura (SSO):** Login via Google OAuth2, emitindo tokens de sessão protegidos pelo Spring Security sem armazenamento de senhas.
* **Autorização Baseada em Perfil:** Endpoint `/api/usuario/me` que define dinamicamente os privilégios de acesso (Equipe de TI vs. Diretoria/Secretaria) com base no domínio e e-mail.
* **Mapeamento Automático de Origem:** Identificação algorítmica da escola requerente através da análise do e-mail institucional do usuário autenticado.
* **Gestão de Ciclo de Vida do Chamado:** Transições de estado rigorosas (Aberto -> Em Andamento -> Resolvido).
* **Consultas Otimizadas:** Endpoints de listagem equipados com paginação (`Pageable`) e ordenação dinâmica via Spring Data.

## 🔒 Variáveis de Ambiente e Segurança

Este projeto adota boas práticas de segurança e não versiona chaves sensíveis. Para rodar o projeto localmente, é necessário configurar um arquivo de propriedades focado em desenvolvimento.

1. Na pasta `src/main/resources`, crie um arquivo chamado `application-dev.properties`.
2. Garanta que este arquivo esteja listado no seu `.gitignore`.
3. Preencha com as suas credenciais do Google Cloud Console e do seu Banco de Dados:
```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:mysql://localhost:3306/edusupport
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

# Credenciais OAuth2 (Google)
spring.security.oauth2.client.registration.google.client-id=SEU_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=SEU_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=profile, email
```
## 🛠️ Como Executar

1. Clone este repositório: `git clone https://github.com/seu-usuario/edusupport-api.git`
2. Certifique-se de ter o **Java 17+** e o **Maven** instalados.
3. Configure o banco de dados MySQL e o arquivo `application-dev.properties` conforme instruído acima.
4. O projeto está configurado para ativar o perfil `dev` automaticamente pelo `application.properties` base.
5. Execute a aplicação via Maven ou através da sua IDE preferida. O servidor iniciará em `http://localhost:8080`.

## 📡 Endpoints Principais

A API responde primariamente na rota `/api/chamados`:

* `POST /api/chamados` - Cria um novo chamado (A escola e data são inferidas no Service).
* `GET /api/chamados` - Lista a fila de chamados (suporta parâmetros `?page=0&size=10&sort=dataAbertura,desc`).
* `PUT /api/chamados/{id}/atender` - Altera o status para 'Em Andamento'.
* `PUT /api/chamados/{id}/resolver` - Altera o status para 'Resolvido'.
* `GET /api/usuario/me` - Retorna o contexto do usuário autenticado pelo Google e seu perfil de acesso.