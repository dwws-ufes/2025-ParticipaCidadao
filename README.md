# 2025-ParticipaCidadao
Assignment for the 2025 edition of the "Web Development and the Semantic Web" course, by Bernardo Seixas Grazziotti, Gabriel de Castro Lima, Gustavo de Andrade Perini and Ricardo Modenese Ramalho

# Repositório de Dockerfiles de desenvolvimento

Este tutorial ensina como configurar seu ambiente de desenvolvimento do Participa Cidadão utilizando Docker. 

**Passo 1:** Crie um diretório vazio na sua Home que irá receber os dados do banco de dados.

**Passo 2:** dentro da pasta docker, que está localizada na raiz do repositório, crie um arquivo chamado `.env` com caminho da pasta que você acabou de criar para o banco de dados. Exemplo:

```
DATABASE_PATH=/home/gustavo/banco
```

**Passo 3:** abra um terminal, vá para o diretório /docker e execute o seguinte comando:

```
docker compose up -d
```

Esse comando vai buildar as imagens e criar todo ambiente de desenvolvimento. Isso pode levar algum tempo uma vez que será efetuado o download de uma série de imagens e todas as depedências do sistema.

**Importante**: o build da imagem do `participacidadao-backfront` pode levar algum tempo (+15min)


Quando tudo finalizar, se tudo deu certo, você deve ter 3 containers rodando na sua máquina. Ao executar o comando `docker ps` você deve encontrar os seguintes containers:

```
IMAGE             PORTS                                                                                      NAMES
phpmyadmin        0.0.0.0:8888->80/tcp, :::8888->80/tcp                                                  participacidadao-phpmyadmin
mysql             0.0.0.0:3306->3306/tcp, :::3306->3306/tcp, 33060/tcp                                   participacidadao-mysql
java_node         0.0.0.0:4200->4200/tcp, :::4200->4200/tcp, 0.0.0.0:8080->8080/tcp, :::8080->8080/tcp   participacidadao-backfront
```

Se esses containers não estiverem ativos, algum problema ocorreu durante a construção das imagens. Tente fazer novamente. Se não conseguir, procure ajuda.

Breve descrição de cada container:
- `participacidadao-mysql`: container com a imagem do banco MySQL. 
- `participacidadao-phpmyadmin`: container com a imagem do PHPMyAdmin. Ele já se conecta com o banco de dados automaticamente.
- `participacidadao-backfront`: este é o container de desenvolvimento do Participa Cidadão. Veja a próxima seção para mais instruções de como usar.

## Utilizando o PHPMyAdmin

O PHPMyAdmin pode ser acessado na sua máquina utilizando o endereço `localhost:8888`. O login padrão é `root` e a senha padrão também é `root`.

Nele você vai encontrar o banco de dados de nome participacidadao. Você pode realizar consultas, remoção, adicionar dados, etc. Para um tutorial de como usar o PHPMyAdmin [assista este vídeo](https://www.youtube.com/watch?v=kviT7G14gqk).


## Utilizando o container de desenvolvimento

O container `participacidadao-backfront` contém tudo que é necessário para executar o front e o backend. A ideia é que você acesse-o com o comando:

```
docker exec -ti amamenta-backfront /bin/bash
```

Feito isso, você ganha acesso a um terminal que está rodando dentro do container. O diretório de trabalho principal é `/app` e ele é mapeado através de um volume com os diretórios da sua máquina. Com isso, você pode:

- Servir apenas o frontend:
  - Primeiro, vamos instalar as dependências do projeto. Entre no diretório `/app/frontend/` e execute o comando `npm install`
    - Como você já deve saber, esse comando vai instalar as dependências do Angular. Tudo vai ser baixado dentro da pasta `node_modules`. Como a pasta está mapeada no volume do docker-compose, você precisa fazer isso uma única vez (será refeito apenas se você atualizar alguma dependência). Essa pasta **não deve ser versionada**, por isso ela está mapeada no `.gitignore`.
  - Uma vez baixadas as dependências, ainda dentro do diretório `/app/participacidadao-frontend/`, execute o comando `ng serve --host 0.0.0.0`. Se tudo deu certo, você acessa o frontend na sua máquina no endereço `localhost:4200`

- Executar o backend
  - Agora abra o diretório `/app/participacidadao-backend/` e execute o comando `mvn spring-boot:run`. Esse comando vai servir o backend no endereço `localhost:8080`
  - Além disso, é necessário que seja seja criado o arquivo application.properties. Ele é importante para fazer a conexão com o banco de dados informando os dados necessários. Crie um diretório chamado resources em `/app/participacidadao-backend/src/main/`. Dentro desse novo diretório crie o arquivo `application.properties`. O arquivo terá o seguinte conteúdo:
    ```
    spring.datasource.url=jdbc:mysql://mysql:3306/participacidadao
    spring.datasource.username=user
    spring.datasource.password=password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

    operation.type=dev

    spring.security.user.name=user
    spring.security.user.password=senha123
    ```