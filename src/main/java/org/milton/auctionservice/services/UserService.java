package org.milton.auctionservice.services; //class belongs to this package


import org.milton.auctionservice.models.User; // importing User class - model representing a user data
import org.milton.auctionservice.repository.UserRepository; // importing UserRepository - repository interface for database operations
import org.springframework.stereotype.Service; // importing Service - Spring annotation to indicate that this class is a service component

import org.mindrot.jbcrypt.BCrypt; //importing BCrypt - library for hashing and verifying passwords

import java.util.List; // importing List - used to return a list of users 
import java.util.Optional; // importing Optional - used for safely handling potential null values

@Service //marking this class as a Spring managed service

/*
* This class registers users securely by hashing their passwords using BCrypt.
* Authenticates users by checking the hashed password against the input password.
* Allows password resets by updating stored potatoes 
* Provides user retrieval by ID or username.
* Lists all users in the database. 
*/
public class UserService { 

    private final UserRepository userRepository; //declares a repository field to interact with the database

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository; // a constructor that injects UserRepository, allowing Spring to manage the dependancy 
    }

    //Register a new user
    public void registerUser(User user) {
        String plainTextPassword = user.getPassword(); //extracts the plain text password from the user object
        String bcryptHash = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt()); // hashes the password using BCrypt.hashpw()
        //which generates a salt and hashes the password - Hashes the password
        user.setPassword(bcryptHash); //sets the hashed password back to the user object
        userRepository.save(user); //saves the user object to the database using the repository
    }

    //Authenticate a user
    public boolean authenticateUser(String username, String password) {
        // Retrieves the user by username
        User user = getUserByUsername(username);
        //checks if user is not null 
        return user != null && BCrypt.checkpw(password, user.getPassword()); // checks the input password against the stored hashed password using BCrypt.checkpw()
        //Returns true if the password matches, false otherwise
    }

    //Reset a user's password
    public void resetPassword(String username, String newPassword) { 
        User user = getUserByUsername(username); //retrieves the user by username
        if (user != null) { //checks if user is not null
            //if user exists 
            String bcryptHash = BCrypt.hashpw(newPassword, BCrypt.gensalt()); //hashes the new password
            user.setPassword(bcryptHash); //sets the hashed password back to the user object
            userRepository.save(user); //saves the user with the new password
        }
    }

    //Fetch all users
    public List<User> getAllUsers() {
        return userRepository.findAll(); //fetches all users from the database using findAll() method of the repository
    }

    //Fetch a user by ID
    public Optional<User> getUserById(Long id) { //wraps the result in an Optional to prevent null pointer exceptions
        return userRepository.findById(id); //Uses findById() method of the repository to fetch a user by ID
    }

    //Fetch a user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username); //Uses findByUsername() method of the repository to fetch a user by username
    }
}
