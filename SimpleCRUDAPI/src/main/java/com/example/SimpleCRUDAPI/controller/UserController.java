package com.example.SimpleCRUDAPI.controller;

// importing necessary libraries
import com.example.SimpleCRUDAPI.entity.User;
import com.example.SimpleCRUDAPI.service.UserService;
import com.example.SimpleCRUDAPI.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// The annotation @RestController marks the class as a controller where http requests are handled.
@RestController
// The annotation @RequestMapping maps HTTP requests to handler methods of MVC
// and REST controllers and defines the base URI to access the REST API.
@RequestMapping()

// The class UserController is a controller class that handles HTTP requests
// related to the User entity.
public class UserController {

    // The annotation @Autowired is used to wire the UserService class to the
    // UserController class.
    @Autowired

    // The private attribute userService is an instance of the UserService class.
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Create a new user
    // The PostMapping annotation maps HTTP POST requests onto specific handler
    // methods.
    @PostMapping("/register")
    // The ReponseEntity class represents an HTTP response, including headers, body,
    // and status and also it is used to return the response body in this case the
    // User object.
    // The @RequestBody annotation extracts the User object from the request body and
    // maps it to the user parameter.
    public ResponseEntity<String> createUser(@RequestBody User user) {
        // Encrypt the password before saving it to the database
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        // Check if the user already exists
        if (userService.getUserByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(409).body("User already exists! Please login");
        }
        User newUser = userService.createUser(user);
        return ResponseEntity.status(201).body( "User created successfully");
    }

    // Login a user and return a JWT token
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User existingUser = userService.getUserByUsername(user.getUsername());

        if (existingUser != null && bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            // Generate JWT token
            String token = jwtUtil.createToken(existingUser.getUsername());
            return ResponseEntity.ok("Bearer " + token);
        }

        return ResponseEntity.status(401).body("Invalid username or password");
    }


    // Get all users
    // The GetMapping annotation maps HTTP GET requests onto specific handler
    // methods.
    @GetMapping("/users")
    // The @PreAuthorize annotation is used to secure the getUsers method by allowing only authenticated users to access it.
    @PreAuthorize("isAuthenticated()")
    // The getUsers method returns a list of all users.
    public ResponseEntity<List<User>> getUsers() {

        // The List<User> class is used to store a list of User objects in the users
        // variable by calling the getUsers method from the userService instance.
        List<User> users = userService.getUsers();
        return users != null ? ResponseEntity.ok(users) : ResponseEntity.notFound().build();
    }

    // Get a user by id
    @GetMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    // The @PathVariable annotation is used to extract the value of a URI template
    // variable and map it to the method parameter.
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    // Update a user
    @PutMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return updatedUser != null ? ResponseEntity.ok("User Updated Successfully") : ResponseEntity.status(404).body("User not found");
    }

    // Delete a user
    @DeleteMapping("/users/{id}")
    @PreAuthorize("isAuthenticated()")
    // Here, the ResponseEntity class is used to return a response with no content.
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.status(404).body("User not found");
    }

}
