package com.yiyongc.javagrpclearn.service;

public interface RatingStore {
  Rating add(String laptopId, double score);
}
