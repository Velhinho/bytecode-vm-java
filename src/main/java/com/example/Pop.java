package com.example;

public class Pop implements Instruction {
  @Override
  public void exec(Environment env) {
    env.pop();
  }
}
