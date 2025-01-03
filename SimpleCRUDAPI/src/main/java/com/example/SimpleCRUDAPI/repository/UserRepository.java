package com.example.SimpleCRUDAPI.repository;


import com.example.SimpleCRUDAPI.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // The UserRepository interface extends the JpaRepository interface which provides CRUD operations for the User entity. Repository in java is a mechanism for encapsulating storage, retrieval, and search behavior which emulates a collection of objects in memory. The @Repository annotation is used to indicate that the class provides the mechanism for storage, retrieval, search, update, and delete operation on objects. Repository is a DAO (Data Access Object) pattern implementation that provides a way to interact with the database.
    // here we are extending the JpaRepository interface and passing the User entity and the type of the primary key of the User entity which is Long. we don't need to provide any implementation for the methods in the JpaRepository interface as it provides the implementation for the CRUD operations. JpaRepository is a JPA specific extension of the Repository interface. It contains the full API of CrudRepository and PagingAndSortingRepository. 
}