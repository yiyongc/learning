package com.yiyongc.javagrpclearn.service;

import com.yiyongc.javagrpclearn.pb.Filter;
import com.yiyongc.javagrpclearn.pb.Laptop;
import io.grpc.Context;

public interface LaptopStore {
    void save(Laptop laptop) throws Exception; // Consider using a separate db dto
    Laptop find(String id);
    void search(Context context, Filter filter, LaptopStream stream);
}

