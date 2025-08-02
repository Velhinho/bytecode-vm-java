package com.example;

public class Halt implements Instruction {
  @Override
  public void exec(Environment env) {
    env.setHalted(true);
  }
}
