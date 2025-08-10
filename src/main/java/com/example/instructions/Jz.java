package com.example.instructions;

import com.example.Environment;

public class Jz implements Instruction {
  private final int address;

  public Jz(int address) {
    this.address = address;
  }

  @Override
  public void exec(Environment env) {
    var top = env.pop();
    if (top == 0) {
      env.setPc(address - 1);
    }
  }
}
