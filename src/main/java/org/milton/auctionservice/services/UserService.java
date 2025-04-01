package org.milton.auctionservice.services;


import org.milton.auctionservice.models.User;
import org.milton.auctionservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        String plainTextPassword = user.getPassword();
        String bcryptHash = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
        user.setPassword(bcryptHash);
        userRepository.save(user);
    }

    public boolean authenticateUser(String username, String password) {
        // Find the user by username
        User user = getUserByUsername(username);
        return user != null && BCrypt.checkpw(password, user.getPassword());
    }

    public void resetPassword(String username, String newPassword) {
        User user = getUserByUsername(username);
        if (user != null) {
            String bcryptHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            user.setPassword(bcryptHash);
            userRepository.save(user);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
