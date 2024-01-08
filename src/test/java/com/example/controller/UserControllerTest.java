package com.example.controller;

import com.example.controller.UserController;
import com.example.model.User;
import com.example.service.NationalityService;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private NationalityService nationalityService;

    @Test
    public void testCreateUser() {
        // Mocking
        when(userService.saveUser(any(User.class)))
                .thenReturn(new User("John Doe", 25, "Male", LocalDate.of(1995, 1, 1), "US", "VERIFIED"));

        // Test
        ResponseEntity<List<User>> response = userController.createUser(1);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        List<User> savedUsers = response.getBody();
        assertEquals(1, savedUsers.size());
        User savedUser = savedUsers.get(0);
        assertEquals("John Doe", savedUser.getName());
        // Add more assertions based on your actual user structure
    }

    @Test
    public void testGetUsers() {
        // Mocking
        when(userService.getAllUsers())
                .thenReturn(Arrays.asList(
                        new User("Alice", 30, "Female", LocalDate.of(1992, 5, 10), "CA", "VERIFIED"),
                        new User("Bob", 28, "Male", LocalDate.of(1994, 3, 15), "US", "VERIFIED")
                ));

        // Test
        ResponseEntity<List<User>> response = userController.getUsers("Name", "ODD", 2, 0);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<User> users = response.getBody();
        assertEquals(2, users.size());
        // Add more assertions based on your actual user structure
    }

    @Test
    public void testGetUsersByGender() {
        // Mocking
        when(userService.getUsersByGender("Male"))
                .thenReturn(Arrays.asList(
                        new User("Bob", 28, "Male", LocalDate.of(1994, 3, 15), "US", "VERIFIED")
                ));

        // Test
        ResponseEntity<List<User>> response = userController.getUsersByGender("Male", "Name", "ODD", 5, 0);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<User> users = response.getBody();
        assertEquals(1, users.size());
        // Add more assertions based on your actual user structure
    }

    @Test
    public void testGetNationalityByName() {
        // Mocking
        when(nationalityService.getNationality("Rishaan"))
                .thenReturn("{" +
                        "\"country\": [" +
                        "{ \"country_id\": \"IN\", \"probability\": 0.632 }" +
                        "]," +
                        "\"name\": \"Rishaan\"" +
                        "}");

        // Test
        ResponseEntity<String> response = userController.getNationalityByName("Rishaan");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        String nationalityInfo = response.getBody();
        // Add more assertions based on the expected nationality information
    }

    // Add more test cases as needed
}
