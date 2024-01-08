package com.example.controller;

import com.example.model.User;
import com.example.service.NationalityService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private NationalityService nationalityService;

    @PostMapping
    public ResponseEntity<List<User>> createUser(@RequestParam(name = "size", defaultValue = "1") int size) {
        List<User> savedUsers = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            // Implement user creation logic and call userService.saveUser()
            // For demonstration purposes, let's create a sample user
            User user = new User();
            user.setName("Sample User " + i);
            user.setAge(30);
            user.setGender("Male");
            user.setDob(LocalDate.of(1990, 1, 1));
            user.setNationality("US");
            user.setVerificationStatus("VERIFIED");
            savedUsers.add(userService.saveUser(user));
        }

        return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(
            @RequestParam(name = "sortType", defaultValue = "Name") String sortType,
            @RequestParam(name = "sortOrder", defaultValue = "ODD") String sortOrder,
            @RequestParam(name = "limit", defaultValue = "5") int limit,
            @RequestParam(name = "offset", defaultValue = "0") int offset) {

        // Implement user retrieval logic and call userService.getAllUsers()
        List<User> users = userService.getAllUsers();

        // Apply sorting, ordering, limit, and offset as needed
        // For demonstration purposes, let's sort by user name
        users.sort((user1, user2) -> {
            if ("Age".equalsIgnoreCase(sortType)) {
                return Integer.compare(user1.getAge(), user2.getAge());
            } else {
                return user1.getName().compareToIgnoreCase(user2.getName());
            }
        });

        // Apply sortOrder logic (even/odd)
        if ("EVEN".equalsIgnoreCase(sortOrder)) {
            users.removeIf(user -> user.getAge() % 2 != 0);
        } else if ("ODD".equalsIgnoreCase(sortOrder)) {
            users.removeIf(user -> user.getAge() % 2 == 0);
        }

        // Apply limit and offset
        int endIndex = Math.min(offset + limit, users.size());
        List<User> paginatedUsers = users.subList(offset, endIndex);

        // Return the list of users along with pagination information
        return ResponseEntity.ok(paginatedUsers);
    }

    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<User>> getUsersByGender(@PathVariable String gender,
                                                       @RequestParam(name = "sortType", defaultValue = "Name") String sortType,
                                                       @RequestParam(name = "sortOrder", defaultValue = "ODD") String sortOrder,
                                                       @RequestParam(name = "limit", defaultValue = "5") int limit,
                                                       @RequestParam(name = "offset", defaultValue = "0") int offset) {
        // Retrieve users by gender from the service
        List<User> users = userService.getUsersByGender(gender);

        // Apply sorting, ordering, limit, and offset as needed
        // Note: Sorting logic is based on the user's name for this example.
        // You can modify it based on your actual sorting requirements.

        users.sort((user1, user2) -> {
            if ("Age".equalsIgnoreCase(sortType)) {
                // Sorting based on age
                return Integer.compare(user1.getAge(), user2.getAge());
            } else {
                // Default sorting based on name (Name or any other attribute you want to sort)
                return user1.getName().compareTo(user2.getName());
            }
        });

        // Apply sortOrder (ODD/EVEN) - For simplicity, this example assumes sorting by name is ascending.
        if ("EVEN".equalsIgnoreCase(sortOrder)) {
            users.removeIf(user -> user.getName().length() % 2 != 0);
        } else {
            users.removeIf(user -> user.getName().length() % 2 == 0);
        }

        // Apply limit and offset
        int endIndex = Math.min(offset + limit, users.size());
        users = users.subList(offset, endIndex);

        // Return the list of users along with pagination information
        return ResponseEntity.ok(users);
    }

    @GetMapping("/nationality/{name}")
    public ResponseEntity<String> getNationalityByName(@PathVariable String name) {
        // Retrieve nationality by name from the service
        String nationalityInfo = nationalityService.getNationality(name);

        if (nationalityInfo != null) {
            // Return the nationality information
            return ResponseEntity.ok(nationalityInfo);
        } else {
            // Handle the case where nationality information could not be retrieved
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve nationality information.");
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        // Retrieve all users from the service
        List<User> users = userService.getAllUsers();

        if (!users.isEmpty()) {
            // Return the list of users
            return ResponseEntity.ok(users);
        } else {
            // Handle the case where no users are found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Add more endpoints as needed
}

