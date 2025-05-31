# Scalable-Final-Project-Wroteit
## Project Overview
This project is a scalable microservices-based platform designed for managing users, communities, threads, moderation, and notifications. Each major feature is implemented as a separate service, allowing for independent development, deployment, and scaling.

## Architecture
- UserApp : Handles user registration, authentication, and profile management.
- CommunitiesApp : Manages community creation, membership, and related operations.
- ThreadsApp : Responsible for thread and post management within communities.
- ModerationApp : Provides moderation tools and workflows for content and user management.
- NotificationApp : Sends notifications to users about relevant events.
- GatewayApp : Acts as an API gateway, routing requests to the appropriate microservice.
- Databases : Uses MongoDB and PostgreSQL for data storage.
- Message Broker : RabbitMQ is used for asynchronous communication between services.
## Development
- Each service is a standalone Spring Boot application (Java), with its own pom.xml and Dockerfile.
- Services communicate via REST APIs and message queues (RabbitMQ).
- The codebase is organized into subfolders for each service, with shared configuration in the root directory.
- API endpoints for each service are documented and tested using Postman collections (see the links in the README).
## Deployment
### Docker Compose
- The root docker-compose.yml file orchestrates all services, databases, and message brokers for local development.
- Each service has its own Dockerfile for containerization.
- To deploy locally:
  1. Ensure Docker is installed and running.
  2. Run:
     ```
     docker-compose up --build
     ```
  3. Access services via their respective ports (see docker-compose.yml for mappings).
### Kubernetes
- The k8s/ directory contains manifests for deploying each service, database, and broker to a Kubernetes cluster.
- To deploy on Kubernetes:
  1. Ensure kubectl is configured for your cluster.
  2. Apply manifests in the correct order (databases, then services):
     ```
     kubectl apply -f k8s/mongo/
     kubectl apply -f k8s/postgres/
     kubectl apply -f k8s/rabbitmq/
     kubectl apply -f k8s/CommunitiesApp/
     kubectl apply -f k8s/GatewayApp/
     kubectl apply -f k8s/ModerationApp/
     kubectl apply -f k8s/NotificationApp/
     kubectl apply -f k8s/ThreadsApp/
     kubectl apply -f k8s/UserApp/
     ```
## Running the Project
- Locally (without Docker):
  1. Install Java and Maven.
  2. For each service, navigate to its directory and run:
     ```
     mvn spring-boot:run
     ```
  3. Ensure MongoDB, PostgreSQL, and RabbitMQ are running (can use Docker for these).
- With Docker Compose:
  - See deployment instructions above.
- With Kubernetes:
  - See deployment instructions above.
## API Testing
- Use the provided Postman collections ( UsersControllerCollection.json , CommunitiesControllerCollection.json , etc.) to test endpoints for each service.
