package com.example;

public class Print implements Instruction {
  @Override
  public void exec(Environment env) {
    env.printTop();
  }
}
