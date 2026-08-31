# Transaction Processing Service

## 1. Problem Understanding

This project implements a small transaction-processing REST service using Java and Spring Boot.
The service manages customer transactions and provides four operations:

- Create a transaction
- Retrieve a transaction by Transaction ID
- Update the status of a transaction
- Retrieve all transactions for a Customer ID

The application uses Spring Data JPA with an H2 in-memory database for persistence.

---

## 2. Assumptions

The following assumptions were made while implementing the service:

- Every transaction must have a unique Transaction ID.
- Every transaction must have a Customer ID.
- The transaction amount must be greater than zero.
- Only INR, USD, and EUR are accepted as currencies.
- Transaction Type is required and can be `DEPOSIT`, `WITHDRAW`, or `TRANSFER`.
- A newly created transaction always starts with `PENDING` status.
- Only transactions with `PENDING` status can have their status changed.
- A `PENDING` transaction can be changed to `COMPLETED`, `FAILED`, or `CANCELLED`. But not to `PENDING` again.
- Once a transaction reaches a final status, its status cannot be changed.
- A request for a transaction that does not exist results in a not-found error.

---

## 3. Validation Rules

Validation is handled at three levels:

- Entity:Basic field validation is done using `@NotBlank`, `@NotNull`, and `@Positive`.
- Controller: `@Valid` validates the request body before it reaches the service layer.
- Service: Business rules are checked, such as duplicate Transaction ID, supported currencies (`INR`, `USD`, `EUR`), required transaction type, and valid status transitions.

Validation errors return `400 Bad Request`, while a transaction that does not exist returns `404 Not Found`.

New transactions are automatically assigned `PENDING` status.

---

## 4. API Endpoints

### Create Transaction

POST /api/transactions

Creates and stores a new transaction.

Example request {In JSON format}:
{
  "transactionId": "TXN100",
  "customerId": "CUST100",
  "amount": 1000.00,
  "currency": "INR",
  "transactionType": "DEPOSIT"
}

The transaction is created with PENDING status.


 ### Get Transaction

 GET /api/transactions/{id}

Retrieves a transaction using its Transaction ID.

Example:

GET /api/transactions/TXN100

Returns 404 Not Found if the transaction does not exist.


### Update Transaction Status

PUT /api/transactions/{id}/status

Updates the status of an existing transaction.

Example request:

{
  "status": "COMPLETED"
}

Only transactions currently in PENDING status can be updated.


### Get Customer Transactions

GET /api/transactions/customer/{customerId}

Retrieves all transactions belonging to the specified Customer ID.

Example:

GET /api/transactions/customer/CUST100

---

## 5. Project Structure

The application is organized into separate layers:

controller - Handles REST API requests.
service - Contains business logic and validation.
repository - Handles database operations using Spring Data JPA.
entity - Defines the Transaction entity.
dto - Contains request objects such as StatusUpdateRequest.
enums - Contains transaction types and statuses.
exception - Contains custom exceptions and global exception handling.

---

## 6. Testing

Automated tests are implemented using JUnit and Spring Boot testing.

The tests cover:

- Successful transaction creation
- Duplicate Transaction ID rejection
- Invalid transaction rejection
- Transaction-not-found handling
- Successful transaction status update
- Prevention of status updates after a transaction reaches a final state

The project can be tested using:

mvnw.cmd clean test (Windows) 
./mvnw clean test (Linux/macOs)

---

## 7. Known Limitations

- The application uses an H2 in-memory database, so transaction data is not persisted after the application stops.
- Currency validation is limited to INR, USD, and EUR.

---

## 8. Improvements With More Time

- Using a persistent database such as MySQL for production use.

