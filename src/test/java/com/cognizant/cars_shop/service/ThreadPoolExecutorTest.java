package com.cognizant.cars_shop.service;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThreadPoolExecutorTest {
    @Test
    public void exec() {
        ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });
        executor.submit(() -> {
            Thread.sleep(1000);
            return null;
        });

        assertEquals(3, executor.getPoolSize());
        assertEquals(0, executor.getQueue().size());
    }
}
