package org.milton.auctionservice.controller; //package declaration - indicating that this file belongs to the controller layer of the application

import org.milton.auctionservice.models.User; // importing User class - model representing a user in the auction system
import org.milton.auctionservice.services.UserService; // importing UserService class - service layer for user-related business logic
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; // importing RestController, RequestMapping, PostMapping, GetMapping, PathVariable - Spring annotations for creating RESTful APIs

import com.auth0.jwt.JWT; // importing JWT class - used to create tokens
import com.auth0.jwt.JWTVerifier; // importing JWTVerifier class - used to verify tokens
import com.auth0.jwt.algorithms.Algorithm; // importing Algorithm class - defines encryption method (RSA in this case)
import com.auth0.jwt.exceptions.JWTCreationException; // importing JWTCreationException class - used to handle exceptions during token creation
import com.auth0.jwt.interfaces.DecodedJWT; // importing DecodedJWT class - used to extract information from a token

//for RSA encryption
import java.security.KeyPair; 
import java.security.KeyPairGenerator; //generates a public-private key pair
import java.security.NoSuchAlgorithmException; //used to handle exceptions related to cryptographic algorithms
import java.security.PrivateKey; // used for encrypting JWTs
import java.security.PublicKey;  //used for decrypting JWTs
import java.security.interfaces.RSAPrivateKey; //used for signing JWTs
import java.security.interfaces.RSAPublicKey; //used for verifying JWTs
import java.util.List; // importing List - used to return a list of users
import java.util.Map; // importing Map - used to create a payload for the JWT
import java.util.Optional; // importing Optional - used for safely handling potential null values


@RestController //declares this as a Spring REST API controller
@RequestMapping("/api/users") //sets the base URL for all endpoints - all endpoints will be prefixed with /api/users
@CrossOrigin(origins = "*") // Allows requests from any domain (important for frontend-backend communication) 
public class UserController {

    private final UserService userService; //declares a userService - instance to interact with the business logic layer
    
    static { //static block to generate RSA keys at application startup
    	KeyPairGenerator generator; //generates RSA keys at application startup
		try { //creates a KeyPairGenerator instance for RSA algorithm
			generator = KeyPairGenerator.getInstance("RSA"); 
		} catch (NoSuchAlgorithmException e) { 
			throw new AssertionError("Could not access specified algorithm.", e); //If an error occurs, it throws an AssertionError.
		}
        //Uses KeyPairGenerator to create a 4096-bit RSA key.
        generator.initialize(4096);
        KeyPair pair = generator.generateKeyPair(); 
        //Stores the public and private keys in static variables.
        privateKey = (RSAPrivateKey) pair.getPrivate();
        publicKey = (RSAPublicKey) pair.getPublic();
    }
    
    //Declares static keys for JWT signing and verification.
    private static final RSAPublicKey publicKey;
    private static final RSAPrivateKey privateKey;

    public UserController(UserService userService) { //Constructor injection - spring automatically provides an instance of UserService.
        this.userService = userService; //allows the controller to use userService methods to handle user-related operations.
    }


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) { //registers a new user
        //if username and password are empty - return 400 bad request
        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
            user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username and password cannot be empty"); 
        }

        //calls userService.registerUser(user) to save the user.
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully"); //returns message indicating success.
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        //Authenticates a user using userService.authenticateUser(username, password)
        boolean isAuthenticated = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok(tokenForUser(user.getUsername())); //if authentication succeeds, generates a JWT token 
        } else {
            return ResponseEntity.status(401).body(null); //otherwise, returns 401 Unauthorized.
        }
    }
    
    public String tokenForUser(String username) {
    	Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey); //Uses Algorithm.RSA256(publicKey, privateKey) for signing.
    	String token = JWT.create() //creates a JWT token using RSA encryption.
    			.withIssuer("auth0") //Sets the issuer of the token.
    			.withPayload(Map.of("username", username)) //Adds username as a payload claim.
    			.sign(algorithm); //signs the token with the private key.
    	return token; //returns the generated token.
    }

    @PostMapping("/userfromtoken")
    public ResponseEntity<User> userFromToken(@RequestBody String token) { 
    	Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey); //Creates an algorithm instance for verifying the token.
    	JWTVerifier verifier = JWT.require(algorithm) //verifies the JWT token
    			.withIssuer("auth0") //Sets the expected issuer.
    			.build(); 

    	DecodedJWT decodedJWT = verifier.verify(token); //decodes the token
    	String username = decodedJWT.getClaim("username").asString(); //Extracts the username from the token.
    	return ResponseEntity.ok(userService.getUserByUsername(username)); //Fetches the user from userService.getUserByUsername(username).
    }

    @PostMapping("/resetpass")
    public ResponseEntity<String> resetPassword(@RequestBody User user) { 
        //resets the password for a user
        userService.resetPassword(user.getUsername(), user.getPassword()); //Calls userService.resetPassword(username, newPassword).
        return ResponseEntity.ok("Reset password successfully."); //returns success message.
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() { //retrieves all users from userService.getAllUsers().
        return ResponseEntity.ok(userService.getAllUsers()); //returns the list of users in response.
    }

    @GetMapping("/{id}") //fetches a specific user by ID
    public ResponseEntity<Optional<User>> getUserById(@PathVariable long id) { //Uses @PathVariable to extract id from the URL.
        return ResponseEntity.ok(userService.getUserById(id)); //Calls userService.getUserById(id) - returns the user object.
}
}
