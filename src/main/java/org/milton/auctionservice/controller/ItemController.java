package org.milton.auctionservice.controller; // package declaration - indicating that this file belongs to the controller layer of the application

import org.milton.auctionservice.exeptions.ResourceNotFoundException; // importing ResourceNotFoundException class - custom exception for handling cases where an item is not found
import org.milton.auctionservice.models.Item; // importing Item class - model representing an item in the auction
import org.milton.auctionservice.services.ItemService; // importing ItemService class - service layer for business logic related to items
import org.springframework.beans.factory.annotation.Autowired; // importing Autowired - Spring annotation to enable automatic dependency injection
import org.springframework.http.ResponseEntity; // importing ResponseEntity class - wrapper for HTTP responses in Spring
import org.springframework.web.bind.annotation.*; // importing RestController, RequestMapping, PostMapping, GetMapping, PutMapping, DeleteMapping - annotations for defining RESTful web services in Spring

import java.util.List; // importing List - used to return a list of items
import java.util.Optional; // importing Optional - used for safely handling potential null values

@RestController // Marks this class as a REST API controller, meaning it handles HTTP requests and responses.
@RequestMapping("/api/items") //defines the base URL for all endpoints in this controller - all endpoints will be prefixed with /api/items
@CrossOrigin(origins = "*") // Allows cross-origin requests from all origins - useful for enabling API to be accessed from different domains (such as frontend hosted on a different server)

public class ItemController { //defines item controller class 

    /*
    * This class accepts a POST request to create a new item
    * Accepts a GET request to fetch an item by its ID
    * Accepts a GET request to fetch all items
    * Accepts a PUT request to update an existing item by its ID
    * Accepts a DELETE request to delete an item by its ID
    */
    private final ItemService itemService; //declares a private itemService - handles business logic related to items

    @Autowired // automatically injects ItemService into ItemController when it is instantiated by Spring - enables the controller to use the itemService methods without having manually instantiate the service class
    public ItemController(ItemService itemService) { 
        this.itemService = itemService; 
    }

    @PostMapping //maps HHTP POST requests to this method - used to create a new item
    public ResponseEntity<Item> createItem(@RequestBody Item item) { //@RequestBody Item item - The request body contains the JSON representation of an Item object, which is automatically mapped to the item parameter.
       //ResponseEntity.created(null) - Returns a response with a 201 Created status code. The null represents the location of the newly created item, though you can replace it with a URI if desired
        return ResponseEntity.created(null).body(itemService.createItem(item)); //itemService.createItem(item) calls the createItem method in the ItemService to create the item in the database.
        // .body(itemService.createItem(item)) - Sends the newly created item as the response body.
    }

    @GetMapping("/{id}") // maps HTTP GET requests for api/items/{id} to this method - fetches an item by its ID
    public ResponseEntity<Item> getItemById(@PathVariable Long id) { //@PathVariable Long id - The {id} in the URL is extracted and mapped to the id parameter.
        Optional<Item> item = itemService.getItemById(id); //itemService.getItemById(id) - Calls the getItemById method in ItemService to fetch the item with the specified ID.
        //item.map(ResponseEntity::ok) - If the item is found, return a 200 OK response with the item.
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); //.orElseGet(() -> ResponseEntity.notFound().build()) - If the item is not found, return a 404 Not Found response.
    }

    @GetMapping // maps HTTP GET requests for api/items to this method - retrieves all items
    public ResponseEntity<List<Item>> getAllItems() { 
        //ResponseEntity.ok() - Returns a 200 OK response with the list of all items as the body.
        return ResponseEntity.ok(itemService.getAllItems()); // itemService.getAllItems() - Calls the getAllItems method in ItemService to fetch all items from the database.
    }

    @PutMapping("/{id}") // maps HTTP PUT requests for api/items/{id} to this method - updates an existing item
    //@PathVariable Long id - extracts the ID of the item to be updated from the URL.
    //@RequestBody Item itemDetails - The request body contains the updated item details in JSON format, which are mapped to itemDetails.
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item itemDetails) {
        try {
            Item updatedItem = itemService.updateItem(id, itemDetails); //itemService.updateItem(id, itemDetails): Calls the updateItem method in ItemService to update the item. If the item is found and updated, it returns the updated item.
            return ResponseEntity.ok(updatedItem); // Returns a 200 OK response with the updated item as the body.
        } catch (ResourceNotFoundException e) { //If an item with the specified ID is not found, it catches the ResourceNotFoundException and returns a 404 Not Found response.
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}") // maps HTTP DELETE requests for api/items/{id} to this method - deletes an item by its ID
    //@PathVariable Long id: Extracts the ID of the item to be deleted from the URL.
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id); //calls the deleteItem method in ItemService to delete the item with the given ID.
        return ResponseEntity.accepted().build(); //Returns a 202 Accepted response, indicating that the request to delete the item has been accepted, though the actual deletion is happening in the background.
    }
}
