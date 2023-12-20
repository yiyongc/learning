package com.yiyongc.javagrpclearn.service;

import com.yiyongc.javagrpclearn.pb.CreateLaptopRequest;
import com.yiyongc.javagrpclearn.pb.CreateLaptopResponse;
import com.yiyongc.javagrpclearn.pb.Laptop;
import com.yiyongc.javagrpclearn.pb.LaptopServiceGrpc;
import com.yiyongc.javagrpclearn.pb.RateLaptopRequest;
import com.yiyongc.javagrpclearn.pb.RateLaptopResponse;
import com.yiyongc.javagrpclearn.sample.Generator;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import java.util.LinkedList;
import java.util.List;
import org.junit.Rule;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LaptopServerTest {

  @Rule public final GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();

  private LaptopStore laptopStore;
  private ImageStore imageStore;
  private RatingStore ratingStore;
  private static LaptopServer server;
  private ManagedChannel channel;

  @BeforeEach
  public void setUp() throws Exception {
    String serverName = InProcessServerBuilder.generateName();
    InProcessServerBuilder serverBuilder =
        InProcessServerBuilder.forName(serverName).directExecutor();

    laptopStore = new InMemoryLaptopStore();
    imageStore = new DiskImageStore("tmp");
    ratingStore = new InMemoryRatingStore();

    server = new LaptopServer(serverBuilder, 0, laptopStore, imageStore, ratingStore);
    server.start();

    channel =
        grpcCleanupRule.register(
            InProcessChannelBuilder.forName(serverName).directExecutor().build());
  }

  @AfterEach
  public void tearDown() throws Exception {
    server.stop();
  }

  @Test
  public void createLaptopWithAValidID() {
    Generator generator = new Generator();
    Laptop laptop = generator.newLaptop();
    CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

    LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
    CreateLaptopResponse response = stub.createLaptop(request);
    assertNotNull(response);
    assertEquals(laptop.getId(), response.getId());

    Laptop found = laptopStore.find(response.getId());
    assertNotNull(found);
  }

  @Test
  public void createLaptopWithAEmptyID() {
    Generator generator = new Generator();
    Laptop laptop = generator.newLaptop().toBuilder().setId("").build();
    CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

    LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
    CreateLaptopResponse response = stub.createLaptop(request);
    assertNotNull(response);
    assertFalse(response.getId().isEmpty());

    Laptop found = laptopStore.find(response.getId());
    assertNotNull(found);
  }

  @Test
  public void createLaptopWithAnInvalidID() {
    Generator generator = new Generator();
    Laptop laptop = generator.newLaptop().toBuilder().setId("invalid").build();
    CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

    LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
    assertThrows(StatusRuntimeException.class, () -> stub.createLaptop(request));
  }

  @Test
  public void createLaptopWithAlreadyExists() throws Exception {
    Generator generator = new Generator();
    Laptop laptop = generator.newLaptop().toBuilder().build();
    laptopStore.save(laptop);
    CreateLaptopRequest request = CreateLaptopRequest.newBuilder().setLaptop(laptop).build();

    LaptopServiceGrpc.LaptopServiceBlockingStub stub = LaptopServiceGrpc.newBlockingStub(channel);
    assertThrows(StatusRuntimeException.class, () -> stub.createLaptop(request));
  }

  @Test
  public void testRateLaptop() throws Exception {
    Generator generator = new Generator();
    Laptop laptop = generator.newLaptop();
    laptopStore.save(laptop);

    LaptopServiceGrpc.LaptopServiceStub stub = LaptopServiceGrpc.newStub(channel);
    RateLaptopResponseStreamObserver responseObserver = new RateLaptopResponseStreamObserver();
    StreamObserver<RateLaptopRequest> requestObserver = stub.rateLaptop(responseObserver);

    double[] scores = {8, 7.5, 10};
    double[] averages = {8, 7.75, 8.5};
    int n = scores.length;

    for (int i = 0; i < n; i++) {
      RateLaptopRequest request =
          RateLaptopRequest.newBuilder().setLaptopId(laptop.getId()).setScore(scores[i]).build();
      requestObserver.onNext(request);
    }

    requestObserver.onCompleted();
    assertNull(responseObserver.err);
    assertTrue(responseObserver.completed);
    assertEquals(n, responseObserver.responses.size());

    int idx = 0;
    for (RateLaptopResponse response : responseObserver.responses) {
      assertEquals(laptop.getId(), response.getLaptopId());
      assertEquals(idx + 1, response.getRatedCount());
      assertEquals(averages[idx], response.getAverageScore(), 1e-9);
      idx++;
    }
  }

  private static class RateLaptopResponseStreamObserver
      implements StreamObserver<RateLaptopResponse> {
    public List<RateLaptopResponse> responses;
    public Throwable err;
    public boolean completed;

    public RateLaptopResponseStreamObserver() {
      responses = new LinkedList<>();
    }

    @Override
    public void onNext(RateLaptopResponse response) {
      responses.add(response);
    }

    @Override
    public void onError(Throwable t) {
      err = t;
    }

    @Override
    public void onCompleted() {
      completed = true;
    }
  }
}
