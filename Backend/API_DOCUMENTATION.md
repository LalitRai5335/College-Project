# Parv Tea Backend - API Documentation

This document lists all the available API endpoints for the Parv Tea website backend, including request and response examples.

## Base URL
`http://localhost:8080`

---

## 1. Authentication
Endpoints for admin access and token management.

### Login
- **Endpoint**: `POST /api/auth/login`
- **Description**: Admin login to receive a JWT token.

**Request Body:**
```json
{
  "username": "admin",
  "password": "admin123"
}
```

**Response (Success):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY4MTkwODAwMCwiZXhwIjoxNjgxOTk0NDAwfQ..."
}
```

---

## 2. Public Endpoints
These endpoints are accessible without any authentication.

### Get Active Products
- **Endpoint**: `GET /api/products`
- **Response Example:**
```json
[
  {
    "id": 1,
    "title": "Masala Tea",
    "subtitle": "Strong & Spicy",
    "description": "A perfect blend of spices...",
    "imageUrl": "/uploads/uuid_masala.jpg",
    "category": "Masala",
    "price": 250.0,
    "active": true
  }
]
```

### Get Approved Reviews
- **Endpoint**: `GET /api/reviews`
- **Response Example:**
```json
[
  {
    "id": 1,
    "reviewerName": "Rahul Sharma",
    "comment": "Best tea I ever had!",
    "rating": 5,
    "role": "Tea Enthusiast",
    "approved": true
  }
]
```

### Submit Contact Form
- **Endpoint**: `POST /api/contact`
- **Request Body:**
```json
{
  "name": "Amit Kumar",
  "email": "amit@example.com",
  "subject": "Bulk Inquiry",
  "message": "I want to order 50 packs of Masala Tea."
}
```
- **Response Example:**
```json
{
  "id": 1,
  "name": "Amit Kumar",
  "email": "amit@example.com",
  "subject": "Bulk Inquiry",
  "message": "I want to order 50 packs of Masala Tea.",
  "createdAt": "2024-04-19T12:00:00"
}
```

### Submit a Review
- **Endpoint**: `POST /api/reviews`
- **Request Body:**
```json
{
  "reviewerName": "Sneha Singh",
  "comment": "Very refreshing!",
  "rating": 4,
  "role": "Customer"
}
```
- **Response Example:**
```json
{
  "id": 2,
  "reviewerName": "Sneha Singh",
  "comment": "Very refreshing!",
  "rating": 4,
  "role": "Customer",
  "approved": false
}
```

---

## 3. Admin Protected Endpoints
Require Header: `Authorization: Bearer <token>`

### Admin Dashboard Stats
- **Endpoint**: `GET /api/admin/dashboard`
- **Response Example:**
```json
{
  "totalProducts": 15,
  "newMessages": 5,
  "totalReviews": 20
}
```

### Product Management (CRUD)
- **Add Product**: `POST /api/admin/products`
  - **Type**: `multipart/form-data`
  - **Params**: `title`, `subtitle`, `description`, `category`, `price`, `image` (file)
- **Response Example:**
```json
{
  "id": 3,
  "title": "Elaichi Tea",
  "imageUrl": "/uploads/uuid_elaichi.jpg",
  "category": "Elaichi",
  "price": 200.0,
  "active": true
}
```

### Review Management
- **Approve Review**: `PATCH /api/admin/reviews/{id}`
- **Response Example:**
```json
{
  "id": 2,
  "reviewerName": "Sneha Singh",
  "approved": true
}
```

---

## 4. Swagger UI (Interactive Docs)
Visit for full schema details: `http://localhost:8080/swagger-ui.html`
