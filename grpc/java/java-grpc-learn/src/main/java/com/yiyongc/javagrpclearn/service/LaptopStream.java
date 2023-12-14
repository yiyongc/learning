package com.yiyongc.javagrpclearn.service;

import com.yiyongc.javagrpclearn.pb.Laptop;

public interface LaptopStream {
    void send(Laptop laptop);
}
