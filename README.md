# Transaction Processing Service

A Spring Boot REST API for processing customer transactions. The service allows creating transactions, retrieving transaction details, updating transaction status, and fetching all transactions belonging to a customer.

## Technology Stack

- Java 17
- Spring Boot 3.5.x
- Spring Data JPA
- H2 In-Memory Database
- Maven
- JUnit 5

---

## Features

The service provides the following functionality:

1. Create a transaction
2. Retrieve a transaction by Transaction ID
3. Update the status of a transaction
4. Retrieve all transactions for a Customer ID

---

## Assumptions

The following assumptions were made during implementation:

- Every transaction must have a unique Transaction ID.
- Every transaction must have a Customer ID.
- Transaction amount must be greater than zero.
- Supported currencies are:
  - INR
  - USD
  - EUR
- Transaction Type is mandatory and must be one of:
  - DEPOSIT
  - WITHDRAW
  - TRANSFER
- New transactions are always created with `PENDING` status.
- Only transactions in `PENDING` status can be updated.
- A `PENDING` transaction can transition to:
  - COMPLETED
  - FAILED
  - CANCELLED
- Once a transaction reaches a final status, it cannot be modified.
- Requests for non-existent transactions return a `404 Not Found`.

---

## Validation Rules

Validation is handled at three levels:

- Entity: Basic field validation is done using @NotBlank, @NotNull, and @Positive.
- Controller: @Valid validates the request body before it reaches the service layer.
- Service: Business rules are checked, such as duplicate Transaction ID, supported currencies (INR, USD, EUR), required transaction type, and valid status transitions.

The following validations are enforced:

- transactionId must not be null, empty, or blank.
- customerId must not be null, empty, or blank.
- amount must be greater than zero.
- currency must be provided and must be one of the supported values: INR, USD, or EUR.
- transactionType must be provided and must be a valid transaction type.
- A transaction cannot be created if another transaction already exists with the same transactionId.
- New transactions are automatically assigned PENDING status regardless of any status value provided by the client.
- A transaction status can only be updated if the current status is PENDING.
- Status updates to the same status (PENDING → PENDING) are not allowed.
- Once a transaction reaches a final status (COMPLETED, FAILED, or CANCELLED), further status updates are rejected.
- Requests for transactions that do not exist result in a 404 Not Found response.

Validation failures and business-rule violations return 400 Bad Request, while requests for non-existent transactions return 404 Not Found.

### Error Responses

| Scenario | Response |
|-----------|----------|
| Invalid input | 400 Bad Request |
| Invalid business rule | 400 Bad Request |
| Transaction not found | 404 Not Found |

---

## Status Transition Rules

The following status transitions are allowed:

| Current Status | Allowed Next Status |
|---------------|----------------------|
| PENDING | COMPLETED |
| PENDING | FAILED |
| PENDING | CANCELLED |

The following transitions are not allowed:

- PENDING → PENDING
- COMPLETED → Any Status
- FAILED → Any Status
- CANCELLED → Any Status

---

## API Endpoints

### Create Transaction

**POST** `/api/transactions`

Creates a new transaction.

#### Example Request

```json
{
  "transactionId": "TXN100",
  "customerId": "CUST100",
  "amount": 1000.00,
  "currency": "INR",
  "transactionType": "DEPOSIT"
}
```

---

### Get Transaction

**GET** `/api/transactions/{id}`

Retrieves a transaction by Transaction ID.

#### Example

```http
GET /api/transactions/TXN100
```

---

### Update Transaction Status

**PUT** `/api/transactions/{id}/status`

Updates the status of an existing transaction.

#### Example Request

```json
{
  "status": "COMPLETED"
}
```

---

### Get Customer Transactions

**GET** `/api/transactions/customer/{customerId}`

Retrieves all transactions for a customer.

#### Example

```http
GET /api/transactions/customer/CUST100
```

---

## Project Structure

```
src
├── main
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── enums
│   ├── exception
│   ├── repository
│   └── service
│
└── test
    └── TransactionServiceTest
```

### Layer Responsibilities

| Layer | Responsibility |
|---------|---------------|
| Controller | Handles REST API requests |
| Service | Business logic and validation |
| Repository | Database access using Spring Data JPA |
| Entity | Domain model |
| DTO | Request/response objects |
| Exception | Custom exceptions and global exception handling |

---

## Running the Application

### Start the Application

Windows:

```bash
mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

---

## Running Tests

Windows:

```bash
mvnw.cmd clean test
```

Linux/macOS:

```bash
./mvnw clean test
```

### Test Coverage

The test suite verifies:

- Successful transaction creation
- Duplicate Transaction ID rejection
- Invalid transaction rejection
- Transaction not found handling
- Successful status update
- Prevention of invalid status transitions

---

## Known Limitations

- Uses an H2 in-memory database.
- Data is lost when the application stops.
- Currency support is limited to INR, USD, and EUR.

---

## Future Improvements

Given more time, the following enhancements could be implemented:

- Replace H2 with MySQL or PostgreSQL

---

## Test Results

The project was tested using:

```bash
mvnw.cmd clean test
```

Result:

```
Tests run: 6
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

---

## AI Usage Disclosure

A separate `AI_USAGE_DISCLOSURE.md` file is included in this repository as required by the assessment instructions.