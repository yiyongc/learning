package com.yiyongc.javagrpclearn.serializer;

import com.google.protobuf.util.JsonFormat;
import com.yiyongc.javagrpclearn.pb.Laptop;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializer {
  public void writeBinaryFile(Laptop laptop, String fileName) throws IOException {
    FileOutputStream outputStream = new FileOutputStream(fileName);
    laptop.writeTo(outputStream);
    outputStream.close();
  }

  public Laptop readBinaryFile(String fileName) throws IOException {
    FileInputStream inputStream = new FileInputStream(fileName);
    Laptop laptop = Laptop.parseFrom(inputStream);
    inputStream.close();
    return laptop;
  }

  public void writeToJSON(Laptop laptop, String fileName) throws IOException {
    JsonFormat.Printer printer = JsonFormat.printer().includingDefaultValueFields().preservingProtoFieldNames();

    String jsonString = printer.print(laptop);

    FileOutputStream outputStream = new FileOutputStream(fileName);
    outputStream.write(jsonString.getBytes());
    outputStream.close();
  }
}
