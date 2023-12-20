package com.yiyongc.javagrpclearn.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryRatingStore implements RatingStore {
  private final ConcurrentMap<String, Rating> data;

  public InMemoryRatingStore() {
    this.data = new ConcurrentHashMap<>();
  }

  @Override
  public Rating add(String laptopId, double score) {
    return data.merge(laptopId, new Rating(1, score), Rating::add);
  }
}
