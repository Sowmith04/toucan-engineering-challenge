# AI Usage Disclosure

## Tools Used

- ChatGPT

## What I Used Them For

I used ChatGPT as a learning and review tool during development of the Spring Boot transaction processing service.

The AI was used to:

- Explain exception handling such as GlobalExceptionHandler and TransactionNotFoundException
- Understand testing concepts and the different components involved in testing
- Understand appropriate HTTP status codes and their usage
- Review and improve code structure
- Understand error messages and identify issues in the code
- Understanding the Git commands

## Significant AI Suggestions

The AI suggested:

- Creating a custom TransactionNotFoundException
- Using a GlobalExceptionHandler with @RestControllerAdvice
- Implementing validation logic inside the service layer
- Writing automated JUnit tests 
- Improving the README structure and documentation

## What I Changed, Corrected, or Rejected

I reviewed all AI suggestions before including them.

Some suggestions were modified to match the assessment requirements and my implementation choices. For example:

- I adjusted validation rules and business logic to match the required transaction workflow.
- I simplified some generated code in Testing to keep the solution easier to understand and explain.
- Some suggested code used approaches that were more complex than necessary for the requirements, so I simplified them.

## What the AI Got Wrong

The AI occasionally suggested code that did not exactly match my project.

Examples included:

- It occasionally suggested method names or class structures that were different from my existing project structure.
- For the status update request, it initially suggested requiring the client to provide the complete transaction details again, whereas my implementation only requires the transaction ID and the new status because only the status needs to be updated.

## How I Verified the Final Result

I manually reviewed all code and verified that I understood every class and method.

To verify correctness:

- I had tested the REST API endpoints using Postman
- I confirmed that all tests completed successfully.
- I reviewed the API endpoints, validation logic, exception handling, and status transition rules to ensure they matched the assignment requirements.

The final submission reflects the version that was successfully tested and verified by me.