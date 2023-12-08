package com.yiyongc.javagrpclearn.serializer;

import com.yiyongc.javagrpclearn.pb.Laptop;
import com.yiyongc.javagrpclearn.sample.Generator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SerializerTest {

  @Test
  void writeAndReadBinaryFile() throws IOException {
    String binaryFile = "laptop.bin";
    Laptop laptop1 = new Generator().newLaptop();

    Serializer serializer = new Serializer();
    serializer.writeBinaryFile(laptop1, binaryFile);

    Laptop laptop2 = serializer.readBinaryFile(binaryFile);
    assertEquals(laptop1, laptop2);
  }

  @Test
  void writeJsonFile() throws IOException {
    Laptop laptop1 = new Generator().newLaptop();

    Serializer serializer = new Serializer();
    serializer.writeToJSON(laptop1, "laptop.json");
  }
}
