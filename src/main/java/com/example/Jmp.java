package com.example;

public class Jmp implements Instruction {
  private final int address;

  public Jmp(int address) {
    this.address = address;
  }

  @Override
  public void exec(Environment env) {
    env.setPc(address);
  }
}
