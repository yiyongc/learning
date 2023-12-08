package com.yiyongc.javagrpclearn.service;

import com.yiyongc.javagrpclearn.exception.AlreadyExistsException;
import com.yiyongc.javagrpclearn.pb.Laptop;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryLaptopStore implements LaptopStore {
  private final ConcurrentMap<String, Laptop> data;

  public InMemoryLaptopStore() {
    data = new ConcurrentHashMap<>(0);
  }

  @Override
  public void save(Laptop laptop) throws Exception {
    if (data.containsKey(laptop.getId())) {
      throw new AlreadyExistsException("laptop ID already exists");
    }
    Laptop other = laptop.toBuilder().build();
    data.put(other.getId(), other);
  }

  @Override
  public Laptop find(String id) {
    if (!data.containsKey(id)) {
      return null;
    }
    return data.get(id).toBuilder().build();
  }
}
