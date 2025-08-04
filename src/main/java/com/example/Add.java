package com.example;

public class Add implements Instruction {
  @Override
  public void exec(Environment env) {
    int b = env.pop();
    int a = env.pop();
    env.push(a + b);
  }
}
