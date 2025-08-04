package com.example;

public class GetGlobal implements Instruction {
  private final String name;
  public GetGlobal(String name) {
    this.name = name;
  }

  @Override
  public void exec(Environment env) {
    env.push(env.getGlobal(name));
  }
}
