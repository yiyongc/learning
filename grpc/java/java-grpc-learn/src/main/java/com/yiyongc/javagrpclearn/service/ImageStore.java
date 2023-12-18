package com.yiyongc.javagrpclearn.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ImageStore {
  String save(String laptopId, String imageType, ByteArrayOutputStream imageData) throws IOException;
}

