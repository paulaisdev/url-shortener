// File: README.md

# URL Shortener - Backend

## Descrição
Uma aplicação cloud-native que simula um sistema de encurtador de URLs. Desenvolvida com Java (Spring Boot), esta aplicação é projetada para rodar em um ambiente Kubernetes provisionado com Terraform.

## Funcionalidades
- Criar uma URL encurtada a partir de uma URL original.
- Listar todas as URLs encurtadas.
- Buscar a URL original a partir do identificador.
- Remover URLs encurtadas pelo ID.

## Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.1.1**
- **PostgreSQL** como banco de dados.
- **Docker** para containerização.
- **Kubernetes** para orquestração de containers.
- **Terraform** para provisionar a infraestrutura em AWS.

## Estrutura do Projeto
```plaintext
project-root/
├── application/       # Código fonte da aplicação Spring Boot
├── infrastructure/
│   ├── terraform/     # Scripts Terraform para provisionamento de infraestrutura
│   ├── kubernetes/    # Manifestos Kubernetes para deploy
├── README.md          # Documentação
```

## Como Rodar Localmente
### Pré-requisitos
- **Docker** e **Docker Compose** instalados.
- **JDK 17** para desenvolvimento.

### Passos
1. Compile a aplicação:
   ```bash
   ./mvnw clean package
   ```
2. Construa a imagem Docker:
   ```bash
   docker build -t url-shortener:latest .
   ```
3. Inicie os serviços com Docker Compose:
   ```bash
   docker-compose up
   ```
4. Acesse a aplicação localmente em `http://localhost:8080`.

### Endpoints da API
#### 1. Criar uma URL encurtada
- **POST** `/shorten`
- **Body**:
  ```json
  {
    "originalUrl": "https://example.com"
  }
  ```
- **Response**:
  ```json
  {
    "shortUrl": "abcd1234"
  }
  ```

#### 2. Buscar URL original
- **GET** `/shorten/{shortId}`
- **Response** (Sucesso):
  ```text
  https://example.com
  ```

#### 3. Listar todas as URLs
- **GET** `/shorten`
- **Response**:
  ```json
  [
    {
      "id": 1,
      "shortId": "abcd1234",
      "originalUrl": "https://example.com"
    }
  ]
  ```

#### 4. Remover uma URL encurtada
- **DELETE** `/shorten/{id}`

---

## Deploy em Kubernetes
### Pré-requisitos
- **kubectl** instalado e configurado.
- **Terraform** instalado.

### Passos
#### Provisionar o Cluster com Terraform
1. Acesse a pasta `infrastructure/terraform`.
2. Inicialize e aplique o Terraform:
   ```bash
   terraform init
   terraform apply
   ```
3. Anote a saída do `cluster_endpoint`.

#### Deploy da Aplicação
1. Acesse a pasta `infrastructure/kubernetes`.
2. Aplique os manifestos:
   ```bash
   kubectl apply -f deployment.yaml
   kubectl apply -f service.yaml
   kubectl apply -f ingress.yaml
   ```

3. Configure o DNS ou atualize o arquivo `hosts` para apontar `url-shortener.local` para o IP do LoadBalancer.

4. Acesse a aplicação em `http://url-shortener.local`.

---

## Melhorias Futuras
- Adicionar suporte para autenticação e autorização.
- Implementar métricas com Prometheus e Grafana.
- Configurar CI/CD para automação de deploys.
