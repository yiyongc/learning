package com.yiyongc.javagrpclearn.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLException;

public class LaptopServer {
  private static final Logger logger = Logger.getLogger(LaptopServer.class.getName());

  private final int port;
  private final Server server;

  public LaptopServer(
      int port, LaptopStore laptopStore, ImageStore imageStore, RatingStore ratingStore) {
    this(ServerBuilder.forPort(port), port, laptopStore, imageStore, ratingStore);
  }

  public LaptopServer(
      int port,
      LaptopStore laptopStore,
      ImageStore imageStore,
      RatingStore ratingStore,
      SslContext sslContext) {
    this(
        NettyServerBuilder.forPort(port).sslContext(sslContext),
        port,
        laptopStore,
        imageStore,
        ratingStore);
  }

  public LaptopServer(
      ServerBuilder serverBuilder,
      int port,
      LaptopStore laptopStore,
      ImageStore imageStore,
      RatingStore ratingStore) {
    this.port = port;
    LaptopService service = new LaptopService(laptopStore, imageStore, ratingStore);
    server =
        serverBuilder.addService(service).addService(ProtoReflectionService.newInstance()).build();
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

  private static SslContext loadTLSCredentials() throws SSLException {
    File serverCertFile = new File("../../cert/server-cert.pem");
    File serverKeyFile = new File("../../cert/server-key.pem");
    File clientCACertFile = new File("../../cert/ca-cert.pem");

    SslContextBuilder ctxBuilder =
        SslContextBuilder.forServer(serverCertFile, serverKeyFile)
//            .clientAuth(ClientAuth.NONE); // Server side TLS
            .clientAuth(ClientAuth.REQUIRE).trustManager(clientCACertFile); // Mutual TLS

    return GrpcSslContexts.configure(ctxBuilder).build();
  }

  public static void main(String[] args) throws InterruptedException, IOException {
    LaptopStore laptopStore = new InMemoryLaptopStore();
    ImageStore imageStore = new DiskImageStore("img");
    RatingStore ratingStore = new InMemoryRatingStore();
    SslContext sslContext = loadTLSCredentials();
    LaptopServer server = new LaptopServer(8080, laptopStore, imageStore, ratingStore, sslContext);
    server.start();
    server.blockUntilShutdown();
  }
}
