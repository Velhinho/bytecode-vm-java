package com.example;

public class Jz implements Instruction {
  private final int address;

  public Jz(int address) {
    this.address = address;
  }

  @Override
  public void exec(Environment env) {
    if (env.pop() == 0) {
      env.setPc(address - 1);
    }
  }
}
