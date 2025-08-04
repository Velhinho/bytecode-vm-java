package com.example;

public class Rem implements Instruction {
  @Override
  public void exec(Environment env) {
    var b = env.pop();
    var a = env.pop();
    env.push(a % b);
  }
}
