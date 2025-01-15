# Teste técnico OTI Sofware

A documentação dos endpoints pode ser consultada rodando o projeto e navegando até http://localhost:8080/swagger-ui.html

### Sobre a arquitetura:

Eu pensei em separar as tabelas em logradouro, cidade, estado, etc, porém achei melhor fazer apenas o simples e resolver
o problema proposto.

Sobre os endpoints de listar CEPs por logradouro e cidade, eu na verdade juntei em um só endpoint, onde é
possivel mandar **2 query parameters** opcionais, com o valor que quer filtrar, por ex:
`/cep/filter?logradouro=Avenida&cidade=Porto`.

Sobre as nomenclaturas do projeto, decidi usar quase tudo em inglês (comentários, nomes de classe, mensagens de commit, etc) pois acredito que seja convenção entre os desenvolvedores.

Para se autenticar nos endpoints, basta em enviar um header "Authorization" com o valor do token gerado ao se autenticar nas rotas de login/register, prefixado com "Bearer ", exemplo: `Bearer xxxxxxxxxxxxxxx`

## Como rodar o projeto

### Com docker:

```sh
# Vai buildar, rodar os testes e gerar uma imagem do docker
./mvnw clean install
# Vai criar os containers da aplicação e do postgres
docker compose up -d
```

### Sem docker:

Sem docker, o banco de dados precisa estar rodando a parte por quem for executar

```shell
./mvnw clean install -Dmaven.test.skip=true
./mvnw spring-boot:run
```

## Testes

Escrevi testes de integração para todos os endpoints usando JUnit

Para maior fidelidade dos testes, foi usado **Testcontainers** para criar uma criar uma instância do PostgreSQL nos
testes de integração (Para ter total fidelidade e não ter que ficar mockando banco de dados),
portanto **para rodar os testes é necessário ter o Docker rodando na sua máquina**.

### Como rodar os testes:

```shell
./mvnw test
```

## Itens opcionais implementados:

- [x] Documentação dos Endpoints em SpringDoc (Swagger)
- [x] Paginar os resultados do JPA usando Spring Page
- [x] Autenticação das requisições com SpringSecurity usando Token JWT
- [x] Migrations em Liquibase (XML)

## Tecnologias usadas:

- [Liquibase](https://github.com/liquibase/liquibase) - Para versionamento do banco de dados
- [Spring Data JPA](https://github.com/spring-projects/spring-data-jpa) - Para persistência de dados
- [Spring Security](https://github.com/spring-projects/spring-security) - Para autenticação das requisições
- [Lombok](https://github.com/projectlombok/lombok) - Gera getters, setters e outros métodos útils durante a compilação
  por anotações.
- [SpringDoc (Swagger)](https://github.com/springdoc/springdoc-openapi) - Para documentação dos endpoints da API
- [PostgreSQL](https://github.com/pgjdbc/pgjdbc) - Banco de dados
- [JUnit (Jupiter)](https://github.com/junit-team/junit5) - Testes de integração e unitários
- [Testcontainers](https://github.com/testcontainers/testcontainers-java) - Foi utilizado nos testes de integração,
  permite criar instâncias reais do PostgreSQL através do
  Docker
