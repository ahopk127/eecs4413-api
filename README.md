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

## API Endpoints List

### Items

- `GET /api/items/` - Returns an array of all items' data.
- `POST /api/items/` - Creates a new item from JSON provided in the POST data, returning the item's database entry.
- `GET /api/items/<id>` - Gets data about the item with id `id`.
- `PUT /api/items/<id>` - Updates the item with id `id`, using JSON provided in the PUT data.
- `DELETE /api/items/<id>` - Deletes the item with id `id`.

### Miscellaneous

- `POST /api/main/bid` - Bids on an item.  JSON data should include the parameters `itemId`, `userId` and `bidAmount`.
- `POST /api/main/pay_now` - Pays for an item.  JSON data should include the parameters `customerId`, `itemId` and `shipping` (a boolean that is true iff expedided shipping was used).

### Users

- `GET /api/users/all` - Returns an array of all users' data.
- `POST /api/users/login` - Accepts a `username` and a `password`.  If they match that of an existing user, returns a token that can be stored in the browser.  If not, returns a status 401.
- `POST /api/users/register` - Accepts user data and creates a new user account.
- `POST /api/users/resetpass` - Accepts a `username` and a `password`.  Changes the associated user's password.
- `POST /api/users/userfromtoken` - Accepts a token returned by `/api/users/login` (don't give it a parameter name, just pass it as a string), and returns the associated user's data.
- `GET /api/users/<id>` - Gets the data of the user with id `id`.

