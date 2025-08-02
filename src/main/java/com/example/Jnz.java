package com.example;

public class Jnz implements Instruction {
  private final int address;

  public Jnz(int address) {
    this.address = address;
  }

  @Override
  public void exec(Environment env) {
    if (env.pop() != 0) {
      env.pop();
      env.setPc(address - 1);
    }
  }
}
