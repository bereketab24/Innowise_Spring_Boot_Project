package com.example.SimpleCRUDAPI.controller;

// Import the RestController and PostMapping annotations from Spring Framework to define HTTP endpoints.
import org.springframework.web.bind.annotation.*;
// Import the ResponseEntity class to wrap HTTP responses and set their status.
import org.springframework.http.ResponseEntity;
// Import the Autowired annotation to inject dependencies automatically.
import org.springframework.beans.factory.annotation.Autowired;
// Import the JwtUtil class to generate and validate JWT tokens.
import com.example.SimpleCRUDAPI.util.JwtUtil;
// Import the UserRepository to fetch user data from the database.
import com.example.SimpleCRUDAPI.repository.UserRepository;
// Import the User entity to work with user data.
import com.example.SimpleCRUDAPI.entity.User;

@RestController
@RequestMapping("/auth")
// The AuthenticationController class handles login requests.
public class AuthenticationController {

    // Inject the JwtUtil utility for generating and validating JWT tokens.
    @Autowired
    private JwtUtil jwtUtil;

    // Inject the UserRepository to fetch user data from the database.
    @Autowired
    private UserRepository userRepository;

    /**
     * The login method handles POST requests to the "/auth/login" endpoint.
     * It accepts username and password, validates them, and returns a JWT token if
     * valid.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        // Step 1: Find the user by username in the database.
        User user = userRepository.findByUsername(username);

        // Step 2: Check if the user exists and the password matches.
        if (user == null || !user.getPassword().equals(password)) {
            // If the user doesn't exist or the password is incorrect, return a 401
            // Unauthorized status.
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // Step 3: Generate a JWT token for the authenticated user.
        String token = jwtUtil.createToken(user.getUsername());

        // Step 4: Return the token in the response body with a 200 OK status.
        return ResponseEntity.ok(token);
    }
}
