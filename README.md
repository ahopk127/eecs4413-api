# Auction Service (Backend)

A Spring Boot-based auction platform where users can list items, place bids, and manage transactions.

This application works with the frontend at [ahopk127/eecs4413-ui](https://github.com/ahopk127/eecs4413-ui/).

## Installation Instructions

Prerequisites:

1. Java 17+
1. Maven 3+
1. Eclipse IDE (optional; for importing the project)
1. Postman (for API testing)
1. SQLite or MySQL

### Steps to Install the Application with Eclipse

1. Import the project into Eclipse
1. Open Eclipse IDE.
1. Click File > Import > Existing Maven Projects.
1. Browse and select the auction-service project folder.
1. Click Finish to import the project.

### Steps to Run the Application with Eclipse

1. Navigate to src/main/java/org/milton/auctionservice/.
1. Locate AuctionServiceApplication.java.
1. Right-click the file and select Run As > Java Application.
1. The server should now be running at http://localhost:8085

### Steps to Run the Application on the Commandline

1. Navigate to the root directory.
1. Run `./mvnw spring-boot:run`.
1. The server should now be running at http://localhost:8085

## Test API Endpoints in Postman

Open Postman.

Use the provided Postman collection (attached in the submission) to test API endpoints.

Example request:

```
POST http://localhost:8085/api/items

{
    "name": "Table",
    "description": "Wonderful Table",
    "auctionType": "FORWARD",
    "duration": "2025-03-14T10:00:00Z",
    "startingPrice": 10000,
    "currentPrice": 0,
    "status": "AVAILABLE"
}
```

Ensure responses are successful.
