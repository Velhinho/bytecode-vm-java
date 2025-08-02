package com.example;

public class Print implements Instruction {
  @Override
  public void exec(Environment env) {
    System.out.println(env.pop());
  }
}
