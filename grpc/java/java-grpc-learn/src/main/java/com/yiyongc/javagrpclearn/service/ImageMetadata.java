package com.yiyongc.javagrpclearn.service;

public class ImageMetadata {
  private String laptopId;
  private String type;
  private String path;

  public ImageMetadata(String laptopId, String type, String path) {
    this.laptopId = laptopId;
    this.type = type;
    this.path = path;
  }

  public String getLaptopId() {
    return laptopId;
  }

  public void setLaptopId(String laptopId) {
    this.laptopId = laptopId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
