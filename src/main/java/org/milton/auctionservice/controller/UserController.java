package org.milton.auctionservice.controller;

import org.milton.auctionservice.models.User;
import org.milton.auctionservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow all origins
public class UserController {

    private final UserService userService;
    
    static {
    	KeyPairGenerator generator;
		try {
			generator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError("Could not access specified algorithm.", e);
		}
        generator.initialize(4096);
        KeyPair pair = generator.generateKeyPair();
        privateKey = (RSAPrivateKey) pair.getPrivate();
        publicKey = (RSAPublicKey) pair.getPublic();
    }
    
    private static final RSAPublicKey publicKey;
    private static final RSAPrivateKey privateKey;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
            user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username and password cannot be empty");
        }

        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        boolean isAuthenticated = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok(tokenForUser(user.getUsername()));
        } else {
            return ResponseEntity.status(401).body("Login failed - incorrect username or password.");
        }
    }
    
    public String tokenForUser(String username) {
    	Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
    	String token = JWT.create()
    			.withIssuer("auth0")
    			.withPayload(Map.of("username", username))
    			.sign(algorithm);
    	return token;
    }

    @PostMapping("/userfromtoken")
    public ResponseEntity<User> userFromToken(@RequestBody String token) {
    	Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
    	JWTVerifier verifier = JWT.require(algorithm)
    			.withIssuer("auth0")
    			.build();

    	DecodedJWT decodedJWT = verifier.verify(token);
    	String username = decodedJWT.getClaim("username").asString();
    	return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @PostMapping("/resetpass")
    public ResponseEntity<String> resetPassword(@RequestBody User user) {
        userService.resetPassword(user.getUsername(), user.getPassword());
        return ResponseEntity.ok("Reset password successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}
