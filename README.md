# ADA University - INFT 2405: Web & Mobile 2 (Integrated Systems)
## Application Setup and Testing Instructions

This project consists of two separate Spring Boot applications: `ass3-ada-server` and `ass3-ada-client`, designed to communicate with each other over a network. Both applications are containerized using Docker.

### Prerequisites

Before you can run the applications, make sure you have the following installed on your system:

- **Java JDK 17**: Required to run the Spring Boot applications.
- **Docker**: Used to containerize the applications.
- **Docker Compose**: Utilized to manage multi-container Docker applications.
- **Gradle**: Needed for building the applications (ensure you have a compatible version for your JDK).

### Environment Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/SITE-ADA/as3-testing-and-aspect-oriented-programming-Kenterum.git

   cd as3-testing-and-aspect-oriented-programming-Kenterum
   ```

2. **Build the Applications:**
   Navigate to each application directory and run:
   ```bash
   cd ass3-ada-server
   ./gradlew build
   cd ../ass3-ada-client
   ./gradlew build
   ```

3. **Dockerization:**
   In the root directory where the `docker-compose.yml` file is located, run:
   ```bash
   docker-compose build
   ```

### Running the Applications

To start the applications, use Docker Compose from the root directory:

```bash
docker-compose up
```

This command will start both the client and server applications, which are configured to communicate on predefined ports (8080 for the server and 8081 for the client).

### Testing the Applications

To ensure everything is working as expected, you can send requests to test the RESTful endpoints provided by the server:

#### CRUD Operations for Books

- **Create (POST Request)**: Add a new book
  ```bash
  curl -X POST http://localhost:8081/api/books -H 'Content-Type: application/json' -d '{"title": "New Book", "author": "Author Name"}'
  ```

- **Read All (GET Request)**: Retrieve all books
  ```bash
  curl http://localhost:8081/api/books
  ```

- **Read by ID (GET Request)**: Retrieve a book by ID
  ```bash
  curl http://localhost:8081/api/books/{id}
  ```

- **Update (PUT Request)**: Update an existing book
  ```bash
  curl -X PUT http://localhost:8081/api/books/{id} -H 'Content-Type: application/json' -d '{"title": "Updated Book", "author": "Updated Author"}'
  ```

- **Delete (DELETE Request)**: Delete a book by ID
  ```bash
  curl -X DELETE http://localhost:8081/api/books/{id}
  ```

#### CRUD Operations for Authors

- **Create (POST Request)**: Add a new author
  ```bash
  curl -X POST http://localhost:8081/api/authors -H 'Content-Type: application/json' -d '{"name": "New Author"}'
  ```

- **Read All (GET Request)**: Retrieve all authors
  ```bash
  curl http://localhost:8081/api/authors
  ```

- **Read by ID (GET Request)**: Retrieve an author by ID
  ```bash
  curl http://localhost:8081/api/authors/{id}
  ```

- **Update (PUT Request)**: Update an existing author
  ```bash
  curl -X PUT http://localhost:8081/api/authors/{id} -H 'Content-Type: application/json' -d '{"name": "Updated Author"}'
  ```

- **Delete (DELETE Request)**: Delete an author by ID
  ```bash
  curl -X DELETE http://localhost:8081/api/authors/{id}
  ```

### Logs

Logs are configured to be stored in multiple locations:
- Individual logs for each application can be found in `ass3-ada-server/logs` and `ass3-ada-client/logs` inside their respective directories. (You can only see it if you start the application manually)
- General logs are aggregated into `All Logs/client-logs` and `All Logs/server-logs` in the root directory.

### Troubleshooting

If you encounter any issues, start by checking the Docker container logs:
```bash
docker logs [container_id]
```

### Stopping the Applications

To stop the running containers:
```bash
docker-compose down
```

### Cleanup

To remove all Docker images and volumes to start fresh:
```bash
docker-compose down --rmi all -v
```


### Video Link about the Application: 

```
Link Will Be Provided
```

### Author: 
```
Suleyman Mammadov
```
