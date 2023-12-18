package com.yiyongc.javagrpclearn.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DiskImageStore implements ImageStore {
  private String imageFolder;
  private ConcurrentMap<String, ImageMetadata> data;

  public DiskImageStore(String imageFolder) {
    this.imageFolder = imageFolder;
    this.data = new ConcurrentHashMap<>();
  }

  @Override
  public String save(String laptopId, String imageType, ByteArrayOutputStream imageData)
      throws IOException {
    String imageId = UUID.randomUUID().toString();
    String imagePath = String.format("%s/%s%s", imageFolder, imageId, imageType);

    FileOutputStream fileOutputStream = new FileOutputStream(imagePath);
    imageData.writeTo(fileOutputStream);
    fileOutputStream.close();

    ImageMetadata metadata = new ImageMetadata(laptopId, imageType, imagePath);
    data.put(imageId, metadata);

    return imageId;
  }
}
