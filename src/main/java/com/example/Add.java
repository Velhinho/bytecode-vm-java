package com.example;

public class Add implements Instruction {
  @Override
  public void exec(Environment env) {
    int a = env.pop();
    int b = env.pop();
    env.push(a + b);
  }
}
