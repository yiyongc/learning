package com.yiyongc.javagrpclearn.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class InMemoryRatingStoreTest {

  @Test
  void testRatingStoreAdd() {
    InMemoryRatingStore store = new InMemoryRatingStore();

    List<Callable<Rating>> tasks = new LinkedList<>();
    String laptopId = UUID.randomUUID().toString();
    double score = 5;

    int n = 10;
    for (int i = 0; i < n; i++) {
      tasks.add(() -> store.add(laptopId, score));
    }

    Set<Integer> ratedCount = new HashSet<>();
    try (ExecutorService executor = Executors.newWorkStealingPool()) {
      executor.invokeAll(tasks)
          .forEach(
              future -> {
                try {
                  Rating rating = future.get();
                  assertEquals(rating.getSum(), rating.getCount() * score, 1e-9);
                  ratedCount.add(rating.getCount());
                } catch (Exception e) {
                  throw new IllegalStateException();
                }
              });
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    assertEquals(n, ratedCount.size());
    for (int cnt = 1; cnt <= n; cnt++) {
      assertTrue(ratedCount.contains(cnt));
    }
  }
}
