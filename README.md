# Spring Boot Shopping Cart Application

# Overview
This is a feature-rich Shopping Cart backend application developed with Spring Boot. It manages user-specific cart operations, product operations, and order processing. The application adheres to clean architecture principles with proper layering (Controller, Service, Repository) and employs JPA for ORM and MySQL/PostgreSQL (or H2 for dev) as the database.

**Key Features:**
# Cart Management

	Add products to cart

	Update product quantity

	Remove individual items

	Clear the whole cart

# Order Management

	Order from the cart

	View orders specific to a user

	Product Handling

	View products available

	Validation of stock while adding to cart or ordering

# Persistent Storage

	JPA + Hibernate with relational database


# Tech Stack
	Backend: Spring Boot, Spring Security, Spring Data JPA

	Database: MySQL / PostgreSQL / H2 (dev)

	Build Tool: Maven

	Language: Java 17+

	Other: Lombok, ModelMapper, REST APIs

# API Endpoints

GET /api/cart — Get user's cart

PUT /api/cart/update — Add or update product in cart

DELETE /api/cart/item — Delete product from cart

DELETE /api/cart/clear — Clear the whole cart

POST /api/orders/place — Place order from current cart

GET /api/orders/user — View user's previous orders