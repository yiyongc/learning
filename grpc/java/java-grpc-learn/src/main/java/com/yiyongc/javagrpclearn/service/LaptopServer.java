package com.yiyongc.javagrpclearn.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;

public class LaptopServer {
  private static final Logger logger = Logger.getLogger(LaptopServer.class.getName());

  private final int port;
  private final Server server;

  public LaptopServer(int port, LaptopStore laptopStore, ImageStore imageStore) {
    this(ServerBuilder.forPort(port), port, laptopStore, imageStore);
  }

  public LaptopServer(
      ServerBuilder serverBuilder, int port, LaptopStore laptopStore, ImageStore imageStore) {
    this.port = port;
    LaptopService service = new LaptopService(laptopStore, imageStore);
    server = serverBuilder.addService(service).build();
  }

  public void start() throws IOException {
    server.start();
    logger.info("server started on port " + port);

    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  System.err.println("shut down gRPC server because JVM is shutting down");
                  try {
                    LaptopServer.this.stop();
                  } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                  }
                }));
  }

  public void stop() throws InterruptedException {
    if (server != null) {
      server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
  }

  private void blockUntilShutdown() throws InterruptedException {
    if (server != null) {
      server.awaitTermination();
    }
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    InMemoryLaptopStore laptopStore = new InMemoryLaptopStore();
    DiskImageStore imageStore = new DiskImageStore("img");
    LaptopServer server = new LaptopServer(8080, laptopStore, imageStore);
    server.start();
    server.blockUntilShutdown();
  }
}
