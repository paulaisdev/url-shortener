# URL Shortener API 📐🔗

## Mapa da Documentação 🗺️

- [Descrição](#descrição)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Rodando a Aplicação Localmente com Docker Compose](#rodando-a-aplicação-localmente-com-docker-compose)
- [Endpoints e Exemplos de Requisição](#endpoints-e-exemplos-de-requisição)
- [Swagger Documentation](#swagger-documentation)
- [Design da API](#design-da-api)
    - [Requisitos Funcionais](#requisitos-funcionais)
- [Provisionando a Infraestrutura e Deploy no Kubernetes com Kind](#provisionando-a-infraestrutura-e-deploy-no-kubernetes-com-kind)

---

## Descrição 📖

Esta é uma **API de Encurtador de URLs** desenvolvida com **Spring Boot**, permitindo que você encurte URLs, visualize os links originais e exclua os registros de URLs encurtadas. A aplicação é empacotada com **Docker**, orquestrada com **Kubernetes (Kind)** e provisionada com **Terraform** para simular ambientes de produção.

---

## Tecnologias Utilizadas 🛠️

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?logo=kubernetes&logoColor=white)
![Terraform](https://img.shields.io/badge/Terraform-7A1F2A?logo=terraform&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?logo=postgresql&logoColor=white)

---

## Rodando a Aplicação Localmente com Docker Compose 🐳

### Passo 1: Instalar o Docker e o Docker Compose 🖥️

Antes de rodar a aplicação, certifique-se de ter o **Docker** e o **Docker Compose** instalados. Siga as instruções de instalação:

- **Docker**: [Documentação de instalação](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Documentação de instalação](https://docs.docker.com/compose/install/)

### Passo 2: Clonar o Repositório Git

Clone o repositório para sua máquina local:

```bash
git clone https://github.com/your-repo/url-shortener.git
cd url-shortener
```

### Passo 3: Configurar o Docker Compose 🔧

No repositório, você encontrará o arquivo `docker-compose.yml`, que orquestra os containers necessários para a aplicação, incluindo o **PostgreSQL** e a **API**.

```yaml
version: "3.7"

services:
  db:
    image: postgres:13
    environment:
      POSTGRES_DB: urlshortener
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: senha1234@!
    ports:
      - "5432:5432"
    networks:
      - backend

  url-shortener:
    image: your-dockerhub-username/url-shortener:latest
    build:
      context: .
      dockerfile: Dockerfile
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/urlshortener
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: senha1234@!
    ports:
      - "8080:8080"
    depends_on:
      - db
    networks:
      - backend

networks:
  backend:
    driver: bridge
```

### Passo 4: Rodar a Aplicação com Docker Compose 🚀

Execute o comando abaixo para subir os containers:

```bash
docker-compose up --build
```

Isso irá iniciar dois containers:
1. **PostgreSQL**
2. **URL Shortener (sua aplicação)**

### Passo 5: Acessar a API 🌐

Após a aplicação iniciar, ela estará disponível em `http://localhost:8080`.

---

## Endpoints e Exemplos de Requisição 📡

---
## **🔎 Teste local**

Para testar localmente, além de utilizar a interface do swagger, está disponível a collection do postman que disponibilizo <a href="./assets/url-shortener-api-postman-collection.json" target="_blank">aqui</a>

---

### 1. **Encurtar uma URL** 🖋️

- **Método**: `POST /shorten`

Requisição:

```json
{
  "originalUrl": "https://www.example.com"
}
```

Resposta:

```json
{
  "data": {
    "shortUrl": "http://localhost:8080/shorten/abcd1234"
  }
}
```

---

### 2. **Obter a URL original** 🔍

- **Método**: `GET /shorten/{shortId}`

Requisição:

```bash
GET http://localhost:8080/shorten/abcd1234
```

Resposta:

```json
{
  "data": {
    "originalUrl": "https://www.example.com"
  }
}
```

---

### 3. **Excluir URL encurtada** 🗑️

- **Método**: `DELETE /shorten/{shortId}`

Requisição:

```bash
DELETE http://localhost:8080/shorten/abcd1234
```

Resposta:

```json
{
  "data": "URL deleted"
}
```

---

## Swagger Documentation 📑

A documentação da **API** pode ser acessada através do **Swagger**. Para usar o Swagger, basta acessar o seguinte endereço após rodar a aplicação:

```
http://localhost:8080/swagger-ui.html
```

Isso abrirá uma interface onde você pode testar todos os endpoints e ver detalhes sobre cada um deles.

---

## Design da API 🧑‍💻

A aplicação segue os padrões de uma **API REST**. Abaixo estão os requisitos funcionais da aplicação:

### Requisitos Funcionais 📝

A aplicação deve expor uma **API REST** para gerenciar as URLs. Os endpoints disponíveis são:

1. **POST /shorten**: Criar uma nova URL encurtada.
    - **Requisição**: O corpo da requisição deve conter a URL de origem e um identificador (nome).
    - **Resposta**: Retorna a URL encurtada.

   Exemplo de requisição:
   ```json
   {
     "originalUrl": "https://www.example.com"
   }
   ```

2. **GET /shorten**: Listar todas as URLs criadas.
    - **Resposta**: Retorna uma lista de todas as URLs encurtadas.

   Exemplo de resposta:
   ```json
   [
     {
       "shortId": "abcd1234",
       "originalUrl": "https://www.example.com"
     },
     {
       "shortId": "efgh5678",
       "originalUrl": "https://www.example2.com"
     }
   ]
   ```

3. **DELETE /shorten/{id}**: Remover uma URL encurtada específica pelo identificador.
    - **Resposta**: Retorna uma mensagem indicando que a URL foi excluída com sucesso.

   Exemplo de requisição:
   ```bash
   DELETE http://localhost:8080/shorten/abcd1234
   ```

   Exemplo de resposta:
   ```json
   {
     "data": "URL deleted"
   }
   ```

4. **GET /convert/{id}**: Retornar a URL original a partir do identificador da URL encurtada.
    - **Resposta**: Retorna a URL original associada ao identificador da URL encurtada.

   Exemplo de requisição:
   ```bash
   GET http://localhost:8080/convert/abcd1234
   ```

   Exemplo de resposta:
   ```json
   {
     "data": {
       "originalUrl": "https://www.example.com"
     }
   }
   ```

---

## Provisionando a Infraestrutura e Deploy no Kubernetes com Kind 🏗️

### Passo 1: Criar a infraestrutura com Terraform 🌍

Este projeto usa o **Terraform** para provisionar a infraestrutura necessária no **Kubernetes** local com **Kind**.

### 1.1 **Pré-requisitos** ⚙️

- Ter o **Terraform** instalado. Para instalar, siga a [documentação oficial](https://learn.hashicorp.com/tutorials/terraform/install-cli).
- Ter o **Docker Desktop** e o **Kind** configurados localmente.

### 1.2 **Arquivos Terraform** 📂

Dentro do diretório `infrastructure/`, temos os seguintes arquivos de configuração do **Terraform**:

- **provider.tf**: Configura o provedor (AWS, GCP, etc.), mas no seu caso, você não usará nenhum provedor de nuvem. Se não for usá-lo, pode remover esse arquivo.
- **eks_cluster.tf**: Cria o cluster Kubernetes no provedor da sua escolha (não necessário para usar o Kind).
- **vpc.tf**: Define as sub-redes e a rede do provedor.

### 1.3 **Rodar o Terraform** 🔄

Para rodar o **Terraform**, execute os seguintes comandos:

1. **Inicializar o Terraform**:

```bash
terraform init
```

2. **Aplicar a infraestrutura**:

```bash
terraform apply
```

Isso irá provisionar a infraestrutura no provedor escolhido (não necessário se estiver usando apenas o **Kind**).

---

### Passo 2: Deploy no Kubernetes com Kind 🖥️

Após a configuração do **Terraform** (caso decida usá-lo para testes em nuvem), podemos realizar o deploy da aplicação no **Kubernetes** local usando **Kind**.

### 2.1 **Criar o Cluster no Kind** 🏗️

Crie o cluster Kubernetes local com o **Kind**:

```bash
kind create cluster --name url-shortener-cluster
```

### 2.2 **Aplicar os Manifestos Kubernetes** ⚙️

Para rodar a aplicação no Kubernetes, aplique os manifestos:

1. **Deploy**: Criando o deployment da aplicação.

   ```bash
   kubectl apply -f deployment.yaml
   ```

2. **Service**: Expondo a aplicação internamente no cluster.

   ```bash
   kubectl apply -f service.yaml
   ```

3. **Ingress**: Configurando o acesso externo à aplicação.



 ```bash
   kubectl apply -f ingress.yaml
   ```

### 2.3 **Verificar o Status do Deploy** ✅

Verifique se os pods estão rodando corretamente:

```bash
kubectl get pods
```

### 2.4 **Acessar a Aplicação** 🌍

Se você estiver usando **Ingress**, adicione o domínio `url-shortener.local` no seu arquivo `/etc/hosts` (ou equivalente no seu sistema operacional) para que o acesso ao Kubernetes via **Ingress** seja possível.

Agora você pode acessar a API no seu navegador em:

```bash
http://url-shortener.local
```


