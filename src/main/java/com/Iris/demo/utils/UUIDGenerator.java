package com.Iris.demo.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator {

  public static String createUUID() {
    return UUID.randomUUID().toString();
  }
}
