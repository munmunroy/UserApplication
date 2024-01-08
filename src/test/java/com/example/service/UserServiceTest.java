package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testGetAllUsers() {
        // Mocking
        when(userRepository.findAll())
                .thenReturn(Arrays.asList(
                        new User("Alice", 30, "Female", LocalDate.of(1992, 5, 10), "CA", "VERIFIED"),
                        new User("Bob", 28, "Male", LocalDate.of(1994, 3, 15), "US", "VERIFIED")
                ));

        // Test
        List<User> users = userService.getAllUsers();

        // Assertions
        assertEquals(2, users.size());
        // Add more assertions based on your actual user structure
    }

    @Test
    public void testSaveUser() {
        // Mocking
        when(userRepository.save(any(User.class)))
                .thenReturn(new User("John Doe", 25, "Male", LocalDate.of(1995, 1, 1), "US", "VERIFIED"));

        // Test
        User savedUser = userService.saveUser(new User("John Doe", 25, "Male", LocalDate.of(1995, 1, 1), "US", "VERIFIED"));

        // Assertions
        assertEquals("John Doe", savedUser.getName());
        // Add more assertions based on your actual user structure
    }

    // Add more test cases as needed
}

