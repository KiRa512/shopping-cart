# Shopping Cart Project

## Overview

The **Shopping Cart** project is a backend application that allows users to manage their shopping carts. Users can add, remove, and update items, view cart details, and interact with product information. The project is built using **Java** and **Spring Boot**, following best practices for clean architecture and DTO-based data transfer.

## Features

* Create and manage shopping carts for users
* Add, update, and remove items in the cart
* Map entities to DTOs for secure and clean API responses
* Integration-ready for microservices or frontend applications
* Optional: Supports user authentication and authorization (if implemented)

## Technologies Used

* **Language:** Java
* **Framework:** Spring Boot
* **Database:** MySQL 
* **Mapping:** MapStruct for DTO conversion
* **Build Tool:** Maven / Gradle

## Project Structure

```
src/main/java
├── com.example.shoppingcart
│   ├── controller       # REST controllers
│   ├── service          # Business logic
│   ├── repository       # JPA repositories
│   ├── dto              # Data Transfer Objects
│   ├── entity           # Database entities
│   └── mapper           # MapStruct mappers
```

## Installation

1. Clone the repository:

```bash
git clone https://github.com/yourusername/shopping-cart.git
```

2. Navigate to the project directory:

```bash
cd shopping-cart
```

3. Configure database connection in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shopping_cart
spring.datasource.username=root
spring.datasource.password=yourpassword
```

4. Build and run the project:

```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints

| Method | Endpoint          | Description                  |
| ------ | ----------------- | ---------------------------- |
| GET    | /cart/{id}        | Get cart by user ID          |
| POST   | /cart/add         | Add an item to the cart      |
| PUT    | /cart/update      | Update item quantity         |
| DELETE | /cart/remove/{id} | Remove an item from the cart |

*(Adjust based on your actual endpoints)*

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch (`git checkout -b feature/YourFeature`)
3. Make your changes
4. Commit (`git commit -m "Add feature"`)
5. Push (`git push origin feature/YourFeature`)
6. Open a Pull Request

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
