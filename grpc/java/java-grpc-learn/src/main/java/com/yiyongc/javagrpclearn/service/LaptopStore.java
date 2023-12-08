package com.yiyongc.javagrpclearn.service;

import com.yiyongc.javagrpclearn.pb.Laptop;

public interface LaptopStore {
    void save(Laptop laptop) throws Exception; // Consider using a separate db dto
    Laptop find(String id);
}
