package com.yiyongc.javagrpclearn.service;

import com.google.protobuf.ByteString;
import com.yiyongc.javagrpclearn.pb.CreateLaptopRequest;
import com.yiyongc.javagrpclearn.pb.CreateLaptopResponse;
import com.yiyongc.javagrpclearn.pb.Filter;
import com.yiyongc.javagrpclearn.pb.ImageInfo;
import com.yiyongc.javagrpclearn.pb.Laptop;
import com.yiyongc.javagrpclearn.pb.LaptopServiceGrpc;
import com.yiyongc.javagrpclearn.pb.Memory;
import com.yiyongc.javagrpclearn.pb.Memory.Unit;
import com.yiyongc.javagrpclearn.pb.RateLaptopRequest;
import com.yiyongc.javagrpclearn.pb.RateLaptopResponse;
import com.yiyongc.javagrpclearn.pb.SearchLaptopRequest;
import com.yiyongc.javagrpclearn.pb.SearchLaptopResponse;
import com.yiyongc.javagrpclearn.pb.UploadImageRequest;
import com.yiyongc.javagrpclearn.pb.UploadImageResponse;
import com.yiyongc.javagrpclearn.sample.Generator;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;

public class LaptopClient {
  private static final Logger logger = Logger.getLogger(LaptopClient.class.getName());

  private final ManagedChannel channel;
  private final LaptopServiceGrpc.LaptopServiceBlockingStub blockingStub;
  private final LaptopServiceGrpc.LaptopServiceStub asyncStub;

  public LaptopClient(String host, int port) {
    channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
    blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
    asyncStub = LaptopServiceGrpc.newStub(channel);
  }

  public LaptopClient(String host, int port, SslContext sslContext) {
    channel = NettyChannelBuilder.forAddress(host, port).sslContext(sslContext).build();
    blockingStub = LaptopServiceGrpc.newBlockingStub(channel);
    asyncStub = LaptopServiceGrpc.newStub(channel);
  }


  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  public void createLaptop(Laptop laptop) {
    CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();
    CreateLaptopResponse response = CreateLaptopResponse.getDefaultInstance();

    try {
      response = blockingStub.withDeadlineAfter(5, TimeUnit.SECONDS).createLaptop(request);
    } catch (StatusRuntimeException e) {
      if (e.getStatus().getCode() == Status.Code.ALREADY_EXISTS) {
        logger.info("laptop ID already exists");
        return;
      }
      logger.log(Level.SEVERE, "request failed: " + e.getMessage());
      return;
    } catch (Exception e) {
      logger.log(Level.SEVERE, "request failed: " + e.getMessage());
      return;
    }
    logger.info("laptop created with ID: " + response.getId());
  }

  private void searchLaptop(Filter filter) {
    logger.info("search started");

    SearchLaptopRequest request = SearchLaptopRequest.newBuilder().setFilter(filter).build();

    try {
      Iterator<SearchLaptopResponse> responseIterator =
          blockingStub.withDeadlineAfter(5, TimeUnit.SECONDS).searchLaptop(request);

      while (responseIterator.hasNext()) {
        SearchLaptopResponse response = responseIterator.next();
        Laptop laptop = response.getLaptop();
        logger.info("- found: " + laptop.getId());
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "request failed: " + e.getMessage());
      return;
    }

    logger.info("search completed");
  }

  private void uploadImage(String laptopId, String imagePath) throws InterruptedException {
    final CountDownLatch completionLatch = new CountDownLatch(1);
    StreamObserver<UploadImageRequest> requestObserver =
        asyncStub
            .withDeadlineAfter(5, TimeUnit.SECONDS)
            .uploadImage(
                new StreamObserver<>() {
                  @Override
                  public void onNext(UploadImageResponse response) {
                    logger.info("received response:\n" + response);
                  }

                  @Override
                  public void onError(Throwable t) {
                    logger.log(Level.SEVERE, "upload failed: " + t);
                    completionLatch.countDown();
                  }

                  @Override
                  public void onCompleted() {
                    logger.info("image uploaded");
                    completionLatch.countDown();
                  }
                });

    FileInputStream fileInputStream;
    try {
      fileInputStream = new FileInputStream(imagePath);
    } catch (FileNotFoundException e) {
      logger.log(Level.SEVERE, "cannot read image file: " + e.getMessage());
      return;
    }
    String imageType = imagePath.substring(imagePath.lastIndexOf("."));
    ImageInfo info = ImageInfo.newBuilder().setLaptopId(laptopId).setImageType(imageType).build();
    UploadImageRequest request = UploadImageRequest.newBuilder().setInfo(info).build();

    try {
      // Send image info first
      requestObserver.onNext(request);
      logger.info("sent image info:\n" + info);

      // Send image data in chunks
      byte[] buffer = new byte[1024];
      while (true) {
        int n = fileInputStream.read(buffer);
        if (n <= 0) {
          break;
        }
        if (completionLatch.getCount() == 0) {
          return;
        }
        request =
            UploadImageRequest.newBuilder().setChunkData(ByteString.copyFrom(buffer, 0, n)).build();
        requestObserver.onNext(request);
        logger.info("sent image chunk with size " + n);
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "unexpected error: " + e.getMessage());
      requestObserver.onError(e);
      return;
    }
    requestObserver.onCompleted();

    if (!completionLatch.await(1, TimeUnit.MINUTES)) {
      logger.warning("request cannot finish within 1 minute");
    }
  }

  private void rateLaptop(String[] laptopIds, double[] scores) throws InterruptedException {
    CountDownLatch finishLatch = new CountDownLatch(1);
    StreamObserver<RateLaptopRequest> requestObserver =
        asyncStub
            .withDeadlineAfter(5, TimeUnit.SECONDS)
            .rateLaptop(
                new StreamObserver<>() {
                  @Override
                  public void onNext(RateLaptopResponse rateLaptopResponse) {
                    logger.info(
                        "laptop rated: id = "
                            + rateLaptopResponse.getLaptopId()
                            + ", count = "
                            + rateLaptopResponse.getRatedCount()
                            + ", average = "
                            + rateLaptopResponse.getAverageScore());
                  }

                  @Override
                  public void onError(Throwable throwable) {
                    logger.log(Level.SEVERE, "rate laptop failed: " + throwable.getMessage());
                    finishLatch.countDown();
                  }

                  @Override
                  public void onCompleted() {
                    logger.info("rate laptop completed");
                    finishLatch.countDown();
                  }
                });

    int n = laptopIds.length;
    try {
      for (int i = 0; i < n; i++) {
        RateLaptopRequest request =
            RateLaptopRequest.newBuilder().setLaptopId(laptopIds[i]).setScore(scores[i]).build();
        requestObserver.onNext(request);
        logger.info(
            "sent rate laptop request: id = "
                + request.getLaptopId()
                + ", score = "
                + request.getScore());
      }
    } catch (Exception e) {
      logger.log(Level.SEVERE, "unexpected error: " + e.getMessage());
      requestObserver.onError(e);
      return;
    }
    requestObserver.onCompleted();
    if (!finishLatch.await(1, TimeUnit.MINUTES)) {
      logger.warning("request cannot finish within 1 minute");
    }
  }

  private static SslContext loadTLSCredentials() throws SSLException {
    File clientCertFile = new File("../../cert/client-cert.pem");
    File clientKeyFile = new File("../../cert/client-key.pem");
    File serverCACert = new File("../../cert/ca-cert.pem");

    return GrpcSslContexts.forClient()
        .keyManager(clientCertFile, clientKeyFile) // For mutual TLS
        .trustManager(serverCACert)
        .build();
  }

  public static void main(String[] args) throws Exception {
    SslContext sslContext = loadTLSCredentials();
    LaptopClient client = new LaptopClient("0.0.0.0", 8080, sslContext);
    try {
      //      testCreateAndSearchLaptop(client);
      //      testUploadImage(client);
      testRateLaptop(client);
    } finally {
      client.shutdown();
    }
  }

  private static void testCreateAndSearchLaptop(LaptopClient client) {
    Generator generator = new Generator();
    for (int i = 0; i < 10; i++) {
      Laptop laptop = generator.newLaptop();
      client.createLaptop(laptop);
    }

    Memory minRam = Memory.newBuilder().setValue(8).setUnit(Unit.GIGABYTE).build();
    Filter filter =
        Filter.newBuilder()
            .setMaxPriceUsd(3000)
            .setMinCpuCores(4)
            .setMinCpuGhz(2.5)
            .setMinRam(minRam)
            .build();

    client.searchLaptop(filter);
  }

  private static void testUploadImage(LaptopClient client) throws InterruptedException {
    Generator generator = new Generator();
    Laptop laptop = generator.newLaptop();
    client.createLaptop(laptop);
    client.uploadImage(laptop.getId(), "tmp/laptop.jpg");
  }

  private static void testRateLaptop(LaptopClient client) throws InterruptedException {
    Generator generator = new Generator();

    int n = 3;
    String[] laptopIds = new String[n];

    for (int i = 0; i < n; i ++ ) {
      Laptop laptop = generator.newLaptop();
      laptopIds[i] = laptop.getId();
      client.createLaptop(laptop);
    }

    Scanner scanner = new Scanner(System.in);
    while(true) {
      logger.info("rate laptop (y/n)? ");
      String answer = scanner.nextLine();
      if (answer.toLowerCase().trim().equals("n")) {
        break;
      }
      double[] scores = new double[n];
      for (int i = 0; i < n; i++) {
        scores[i] = generator.newLaptopScore();
      }

      client.rateLaptop(laptopIds, scores);
    }

  }
}
