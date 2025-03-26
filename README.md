Auction Service

	A Spring Boot-based auction platform where users can list items, place bids, and manage transactions.

Installation Instructions

	Prerequisites:

-Java 17+

-Maven 3+

-Eclipse IDE (for importing the project)

-Postman (for API testing)

-SQLite or MySQL

	Steps to Run the Server:

-Import the Project into Eclipse:

-Open Eclipse IDE.

-Click File > Import > Existing Maven Projects.

-Browse and select the auction-service project folder.

-Click Finish to import the project.

	Modify Database Configuration:

-Navigate to src/main/java/org/milton/auctionservice/config.

-Open SQLiteConfig.java.

-Locate line 23 and update the database file path:

-dataSource.setUrl("jdbc:sqlite:C:\\Users\\harja\\auction-service\\src\\main\\resources\\db.sqlite3");

-Replace C:\Users\harja\auction-service\src\main\resources\db.sqlite3 with your actual file path for db.sqlite3 located in src/main/resources.

	Run the Application:

-Navigate to src/main/java/org/milton/auctionservice/.

-Locate AuctionServiceApplication.java.

-Right-click the file and select Run As > Java Application.

	The server should now be running at:

http://localhost:8085

Test API Endpoints in Postman:

Open Postman.

Use the provided Postman collection (attached in the submission) to test API endpoints.

Example request:

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

Ensure responses are successful.
