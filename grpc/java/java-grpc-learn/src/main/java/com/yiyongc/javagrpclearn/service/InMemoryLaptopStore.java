package com.yiyongc.javagrpclearn.service;

import com.yiyongc.javagrpclearn.exception.AlreadyExistsException;
import com.yiyongc.javagrpclearn.pb.Filter;
import com.yiyongc.javagrpclearn.pb.Laptop;
import com.yiyongc.javagrpclearn.pb.Memory;

import io.grpc.Context;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class InMemoryLaptopStore implements LaptopStore {
  private static final Logger logger = Logger.getLogger(InMemoryLaptopStore.class.getName());
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
  public void search(Context context, Filter filter, LaptopStream stream) {
    for (Map.Entry<String, Laptop> entry : data.entrySet()) {
      if (context.isCancelled()) {
        logger.info("context is cancelled)");
        return;
      }
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      Laptop laptop = entry.getValue();
      if (isQualified(filter, laptop)) {
        stream.send(laptop.toBuilder().build());
      }
    }
  }

  @Override
  public Laptop find(String id) {
    if (!data.containsKey(id)) {
      return null;
    }
    return data.get(id).toBuilder().build();
  }

  private boolean isQualified(Filter filter, Laptop laptop) {
    if (laptop.getPriceUsd() > filter.getMaxPriceUsd()) {
      return false;
    }
    if (laptop.getCpu().getNumberCores() < filter.getMinCpuCores()) {
      return false;
    }
    if (laptop.getCpu().getMinGhz() < filter.getMinCpuGhz()) {
      return false;
    }
    return toBit(laptop.getRam()) >= toBit(filter.getMinRam());
  }

  private long toBit(Memory memory) {
    long value = memory.getValue();
    return switch (memory.getUnit()) {
      case BIT -> value;
      case BYTE -> value << 3;
      case KILOBYTE -> value << 13;
      case MEGABYTE -> value << 23;
      case GIGABYTE -> value << 33;
      case TERABYTE -> value << 43;
      default -> 0L;
    };
  }
}
