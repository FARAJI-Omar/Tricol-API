# Tricol Fournisseur Management API

This project is a Spring RESTful API for managing Fournisseur (supplier) entities. It supports CRUD operations and sorting, with JSON-based communication.

## Features
- Add a new fournisseur
- Update one or more fournisseur properties
- Delete fournisseur by ID
- Get all fournisseurs
- Get fournisseurs sorted by society name
- Consistent JSON response format

## Endpoints
Base URL: `/api/v1/fournisseurs`

### Add Fournisseur
- **POST** `/add`
- Body (JSON):
  ```json
  {
    "societe": "ABC Corp",
    "adresse": "123 Main St",
    "contact": "John Doe",
    "email": "john@example.com",
    "telephone": "0612345678",
    "ville": "Casablanca",
    "ice": "123456789"
  }
  ```

### Update Fournisseur
- **PUT** `/update/{id}`
- Body (JSON): Only include fields to update. Example:
  ```json
  {
    "email": "newemail@example.com"
  }
  ```

### Delete Fournisseur
- **DELETE** `/delete/{id}`

### Get All Fournisseurs
- **GET** `/all`

### Get Sorted Fournisseurs
- **GET** `/sortBySociety`

## Response Format
All endpoints return a JSON object with:
- `message`: status message
- `status`: HTTP status code
- `data`: result data (entity or list)

## Requirements
- Java 17+
- Maven
- MySQL (or compatible database)


