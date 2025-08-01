package com.example;

public class Sub implements Instruction {
  @Override
  public void exec(Environment env) {
    var a = env.pop();
    var b = env.pop();
    env.push(a - b);
  }
}
