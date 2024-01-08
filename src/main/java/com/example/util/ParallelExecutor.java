package com.example.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ParallelExecutor {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Async
    public <T> CompletableFuture<T> execute(Callable<T> callable) {
        CompletableFuture<T> completableFuture = new CompletableFuture<>();

        executorService.submit(() -> {
            try {
                T result = callable.call();
                completableFuture.complete(result);
            } catch (Exception e) {
                completableFuture.completeExceptionally(e);
            }
        });

        return completableFuture;
    }
}
