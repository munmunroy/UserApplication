package com.example.util;

import com.example.util.ParallelExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParallelExecutorTest {

    @InjectMocks
    private ParallelExecutor parallelExecutor;

    @Mock
    private Callable<Object> callable;

    @Test
    public void testExecute() throws Exception {
        // Mocking
        when(callable.call()).thenReturn("Test Result");

        // Test
        CompletableFuture<Object> future = parallelExecutor.execute(callable);

        // Assertions
        assertEquals("Test Result", future.get());
    }

    // Add more test cases as needed
}
