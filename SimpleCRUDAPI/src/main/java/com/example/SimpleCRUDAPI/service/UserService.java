package com.example.SimpleCRUDAPI.service;

// importing necessary libraries

// import User entity class to use it in the service class to perform CRUD operations on the User entity by calling the methods from the UserRepository interface. User entity class is a simple POJO class that represents the User entity in the database.
import com.example.SimpleCRUDAPI.entity.User;

// import UserRepository interface to use it in the service class to perform CRUD operations on the User entity. UserRepository interface extends the JpaRepository interface which provides CRUD operations for the User entity.
import com.example.SimpleCRUDAPI.repository.UserRepository;


import org.springframework.beans.factory.annotation.Autowired;

// import the @Service annotation to indicate that the class is a service class which is crucial for the component scanning and helps in implementing the dependency injection design pattern. Because of the @Service annotation, the class is automatically registered as a bean in the Spring application context. beans are objects that form the backbone of the application and that are managed by the Spring IoC container. They are used to encapsulate the state of an application and are managed by the Spring IoC container.
import org.springframework.stereotype.Service;

import java.util.List;

// The Optional class is a container object that may or may not contain a non-null value. If a value is present, isPresent() will return true and get() will return the value. The Optional class is used to represent a value which is either present or absent. The Optional class is used to avoid null pointer exceptions.
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // Get a user by id
    public User getUserById(Long id) {
        // here, the Optional and User classes are used to return the user object if it is present in the database, otherwise it returns null.
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    // Get a user by username
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Update a user
    public User updateUser(Long id, User user) {
        // here, the Optional and User classes are used to return the user object if it is present in the database, otherwise it returns null.
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            // here, the .get() method is used to get the user object from the Optional class. It is inherited from the Optional class.
            User updatedUser = existingUser.get();

            // here, the setUsername and setPassword methods are used to update the username and password of the user object. they are inherited from the User class.
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            return userRepository.save(updatedUser);
        } else {
            return null;
        }
    }

    // Delete a user
    public boolean deleteUser(Long id) {
        // here, the Optional and User classes are used to return the user object if it is present in the database, otherwise it returns null.
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            // here, the deleteById method is used to delete the user object from the database. It is inherited from the JpaRepository interface.
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    
}
