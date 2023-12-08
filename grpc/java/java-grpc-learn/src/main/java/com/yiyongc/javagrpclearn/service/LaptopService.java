package com.yiyongc.javagrpclearn.service;

import com.yiyongc.javagrpclearn.exception.AlreadyExistsException;
import com.yiyongc.javagrpclearn.pb.CreateLaptopRequest;
import com.yiyongc.javagrpclearn.pb.CreateLaptopResponse;
import com.yiyongc.javagrpclearn.pb.Laptop;
import com.yiyongc.javagrpclearn.pb.LaptopServiceGrpc;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.netty.util.internal.StringUtil;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class LaptopService extends LaptopServiceGrpc.LaptopServiceImplBase {
  private static final Logger logger = Logger.getLogger(LaptopService.class.getName());

  private final LaptopStore store;

  public LaptopService(LaptopStore laptopStore) {
    this.store = laptopStore;
  }

  @Override
  public void createLaptop(
      CreateLaptopRequest request, StreamObserver<CreateLaptopResponse> responseObserver) {
    Laptop laptop = request.getLaptop();

    String id = laptop.getId();
    logger.info("got a create laptop request with ID: " + id);

    UUID uuid;
    try {
      uuid =
          StringUtil.isNullOrEmpty(laptop.getId())
              ? UUID.randomUUID()
              : UUID.fromString(laptop.getId());
    } catch (IllegalArgumentException e) {
      responseObserver.onError(
          Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException());
      return;
    }

    // heavy processing simulation
//    try {
//      TimeUnit.SECONDS.sleep(6);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }

    if (Context.current().isCancelled()) {
      logger.info("request is cancelled");
      responseObserver.onError(
          Status.CANCELLED.withDescription("request is cancelled").asRuntimeException());
      return;
    }

    Laptop other = laptop.toBuilder().setId(uuid.toString()).build();
    try {
      store.save(other);
    } catch (AlreadyExistsException e) {
      responseObserver.onError(
          Status.ALREADY_EXISTS.withDescription(e.getMessage()).asRuntimeException());
      return;
    } catch (Exception e) {
      responseObserver.onError(
          Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
      return;
    }
    CreateLaptopResponse response = CreateLaptopResponse.newBuilder().setId(other.getId()).build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();

    logger.info("saved laptop with ID: " + other.getId());
  }
}
